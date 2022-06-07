package com.revanth.springbootproducerconsumer.resources;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revanth.springbootproducerconsumer.models.Employee;

@RestController
@RequestMapping(value = "kafka")
public class ProducerAPI {
	
	private static final Log log = LogFactory.getLog(ProducerAPI.class);
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplateString;
	
	@Autowired
	private KafkaTemplate<String, Employee> kafkaTemplateEmployee;
	
	@Value("${topic.string}")
	private String TOPIC;	
	
	@Value("${topic.employee}")
	private String JSONTOPIC;
	
	//REST API for producing string message
	@PostMapping("/publish/{message}")
	public ResponseEntity<String> publishStringMessage(@PathVariable String message){
		kafkaTemplateString.send(TOPIC,message);
		log.info("Produced message : " +message);
		return new ResponseEntity<String>("Published message : "+message, HttpStatus.CREATED);
	}
	
	//REST API for producing Employee object
	@PostMapping("/publish")
	public ResponseEntity<String> publishStringMessage(@RequestBody Employee employee){
		kafkaTemplateEmployee.send(JSONTOPIC,employee);
		log.info("Produced Employee : " +employee);
		return new ResponseEntity<String>("Successfully published Employee with the id : "+employee.getId(), HttpStatus.CREATED);
	}
}
