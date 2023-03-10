package com.inconsistency.javakafka.kafkajava.controller;

import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inconsistency.javakafka.kafkajava.services.ReceiveModifications;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaController.class);
	@Autowired
	private ReceiveModifications receiveModifications;

	@PostMapping(value = "/send")
	public int send(@RequestParam String filePath, @RequestParam String version) {
		try {
			receiveModifications.parseUML(filePath, version);
			return Response.SC_OK;
		} catch (Exception e) {
			logger.error(e.getMessage());			
		}

		return Response.SC_NOT_FOUND;
	}
}
