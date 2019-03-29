package com.example.project.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.Service.KafkaProducerService;

@RestController
@RequestMapping(value = "/api")
public class KafkaProducerController {
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaProducerController.class);
	
	@Autowired
	KafkaProducerService kafkaProducerService;
	
	@PostMapping(value = "/topic/{topic}/producer/{message}")
    public void sendMessageToKafkaTopic(@PathVariable("topic") String topic, @PathVariable("message") String message) {
		logger.info(topic+" -- "+message);
		kafkaProducerService.sendMessage(topic, message);
    }

}
