package com.inconsistency.javakafka.kafkajava.inconsistency;

import java.io.Serializable;

public class InconsistencyErrorModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	String clientId;
	String inconsistencyType;
	String severity;
	String diagram;
	String propertyType;
	String propertyName;
	String umlPackage;
	String description;
	String cr;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String transactionId) {
		this.clientId = transactionId;
	}

	public String getInconsistencyType() {
		return inconsistencyType;
	}

	public void setInconsistencyType(String inconsistencyType) {
		this.inconsistencyType = inconsistencyType;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getDiagram() {
		return diagram;
	}

	public void setDiagram(String diagram) {
		this.diagram = diagram;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getUmlPackage() {
		return umlPackage;
	}

	public void setUmlPackage(String umlPackage) {
		this.umlPackage = umlPackage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCr() {
		return cr;
	}

	public void setCr(String cr) {
		this.cr = cr;
	}
}
