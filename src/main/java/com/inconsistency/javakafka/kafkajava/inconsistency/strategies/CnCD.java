package com.inconsistency.javakafka.kafkajava.inconsistency.strategies;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.inconsistency.javakafka.kafkajava.inconsistency.Inconsistency;
import com.inconsistency.javakafka.kafkajava.inconsistency.InconsistencyError;
import com.inconsistency.javakafka.kafkajava.inconsistency.InconsistencyType;
import com.inconsistency.javakafka.kafkajava.inconsistency.Severity;
import com.inconsistency.javakafka.kafkajava.uml.models._class.ClassStructure;
import com.inconsistency.javakafka.kafkajava.uml.models._sequence.SequenceLifeline;
import com.inconsistency.javakafka.kafkajava.uml.reader.diagram.DiagramProperties;

@Component
public class CnCD extends Inconsistency {
	
	public CnCD() {
		super(InconsistencyType.CnCD, Severity.HIGH);
	}
	
	@Override
	@KafkaListener(topics = ("${tpd.topic-name}" + "." + "CnCD"), clientIdPrefix = "CnCD",
            containerFactory = "kafkaListenerContainerFactory")
    public void listenTopic(ConsumerRecord<String, DiagramProperties> cr,
                               @Payload DiagramProperties payload) {
		super.listenTopic(cr, payload);        	
    }
	
	@Override
	public void analyse() {			
		Map<String, ClassStructure> classesMessageMap = new HashMap<>();
		
		for (ClassStructure classStructure: this.getClassDiagram().getClasses()) {
			classesMessageMap.put(classStructure.getName(), classStructure);
		}
		
		for (SequenceLifeline lifeline: this.getSequenceDiagram().getLifelines()) {
			if (classesMessageMap.get(lifeline.getLifelineName()) == null) {
				String errorMessage = "O objeto " + lifeline.getLifelineName() + " não foi definido no diagrama de classes.";
				InconsistencyError error = new InconsistencyError("object", lifeline.getLifelineName(), null, errorMessage);
				this.addError(error);
			}
		}			
	}
}
