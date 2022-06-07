package com.revanth.springbootproducerconsumer.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.revanth.springbootproducerconsumer.models.Employee;


@Configuration
@EnableKafka
public class KafkaConfiguration {
	
	@Bean
	public ProducerFactory<String, String> stringProducerFactory() {
		Map<String, Object> config = new HashMap<String, Object>();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return new DefaultKafkaProducerFactory<String, String>(config );
	}
	
	@Bean
	public KafkaTemplate<String, String> stringKafkaTemplate(){
		return new KafkaTemplate<String, String>(stringProducerFactory());
	}
	
	@Bean
	public ProducerFactory<String, Employee> employeeProducerFactory() {
		Map<String, Object> config = new HashMap<String, Object>();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<String, Employee>(config );
	}
	
	@Bean
	public KafkaTemplate<String, Employee> employeeKafkaTemplate(){
		return new KafkaTemplate<String, Employee>(employeeProducerFactory());
	}
	

	// ConsumerFactory for the String type
	@Bean
	public ConsumerFactory<String,String> strinConsumerFactory() {
		Map<String,Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		config.put(ConsumerConfig.GROUP_ID_CONFIG,"group-string");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(config);
	}
	
	// ConcurrentKafkaListenerContainerFactory for the String type
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String , String> stringListnerContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(strinConsumerFactory());
		return factory;
	}
	
	// ConsumerFactory for the Employee object
	@Bean
	public ConsumerFactory<String,Employee> employeeConsumerFactory() {
		Map<String,Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		config.put(ConsumerConfig.GROUP_ID_CONFIG,"group-json");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(Employee.class));
	}
	
	// ConcurrentKafkaListenerContainerFactory for the Employee object
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String , Employee> employeeListnerContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<String, Employee> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(employeeConsumerFactory());
		return factory;
	}

}
