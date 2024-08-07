package com.inconsistency.javakafka.kafkajava.uml.reader.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;

import com.inconsistency.javakafka.kafkajava.entities.uml.dto.UMLModelDTO;
import com.inconsistency.javakafka.kafkajava.entities.uml.models._class.ClassDiagram;
import com.inconsistency.javakafka.kafkajava.entities.uml.models._class.ClassInstance;
import com.inconsistency.javakafka.kafkajava.entities.uml.models._class.ClassStructure;
import com.inconsistency.javakafka.kafkajava.entities.uml.models._enum.EnumStructure;
import com.inconsistency.javakafka.kafkajava.entities.uml.models._package.PackageStructure;
import com.inconsistency.javakafka.kafkajava.uml.reader.PackageReader;
import com.inconsistency.javakafka.kafkajava.uml.reader.ReaderUtils;

public class ClassDiagramReader implements Serializable {
	private static final long serialVersionUID = 1L;

	public static ClassDiagram getRefModelDetails(Package _package) throws Exception {
		if (_package == null) {
			throw new Exception("[Model] Package is null");
		}
	
		EList<PackageableElement> packageableElements = _package.getPackagedElements();
		String packageName = _package.getName() != null ? _package.getName() : "";
		PackageStructure packageStructure = PackageReader.readPackage(packageableElements, packageName, _package);

		ArrayList<ClassStructure> classes = classStructures(packageStructure);
		for (ClassStructure cs : classes) {
			List<ClassStructure> superClasses = new ArrayList<>();
			for (ClassStructure superClass : cs.getSuperClasses()) {
				ClassStructure superClassByName = getClassByName(classes, superClass.getName());
				if (superClassByName != null) {
					superClasses.add(superClassByName);
				}
			}
			cs.setSuperClasses(superClasses);
		}

		ArrayList<ClassInstance> instances = classInstances(packageStructure);
		for (ClassInstance classInstance : instances) {
			for (ClassStructure classStructure : classInstance.getClasses()) {
//                classes.get(classStructure.getName()).getInstances().add(classInstance);
				getClassByName(classes, classStructure.getName()).getInstances().add(classInstance);
			}
		}

		ClassDiagram umlModel = new ClassDiagram();
		umlModel.getEnumerations().addAll(enumStructure(packageStructure));
		umlModel.getClasses().addAll(classes);
		umlModel.getInstances().addAll(instances);

		return umlModel;
	}

	private static ArrayList<ClassInstance> classInstances(PackageStructure packageStructure) {
		ArrayList<ClassInstance> instances = new ArrayList<>();

		for (ClassInstance classInstance : packageStructure.getInstances()) {
			instances.add(classInstance);
		}

		for (PackageStructure ps : packageStructure.getPackages()) {
			instances.addAll(classInstances(ps));
		}
		return instances;
	}

	private static ArrayList<ClassStructure> classStructures(PackageStructure packageStructure) {
		ArrayList<ClassStructure> classes = new ArrayList<>();

		for (ClassStructure classStructure : packageStructure.getClasses()) {
			classes.add(classStructure);
		}

		for (PackageStructure ps : packageStructure.getPackages()) {
			classes.addAll(classStructures(ps));
		}
		return classes;
	}

	private static ClassStructure getClassByName(ArrayList<ClassStructure> classes, String className) {
		for (ClassStructure classStructure : classes) {
			if (classStructure.getName().equals(className)) {
				return classStructure;
			}
		}

		return null;
	}

	private static ArrayList<EnumStructure> enumStructure(PackageStructure packageStructure) {
		ArrayList<EnumStructure> enums = new ArrayList<>();

		for (EnumStructure classStructure : packageStructure.getEnums()) {
			enums.add(classStructure);
		}

		for (PackageStructure ps : packageStructure.getPackages()) {
			enums.addAll(enumStructure(ps));
		}
		return enums;
	}
}
