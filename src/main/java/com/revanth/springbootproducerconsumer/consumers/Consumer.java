package com.revanth.springbootproducerconsumer.consumers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.revanth.springbootproducerconsumer.models.Employee;

@Service
public class Consumer {
	
	private static final Log log = LogFactory.getLog(Consumer.class);
	
	@KafkaListener(topics = "${topic.string}", groupId = "group-string")
	public void consumeString(String message) {
		log.info("Consumed message : " +message);
	}
	
	@KafkaListener(topics = "${topic.employee}", groupId = "group-json", containerFactory = "employeeListnerContainerFactory")
	public void consumeEmployee(Employee employee) {
		log.info("Consumed Object : " +employee);
	}
}
