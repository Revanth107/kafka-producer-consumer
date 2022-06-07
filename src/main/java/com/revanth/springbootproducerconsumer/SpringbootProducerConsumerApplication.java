package com.revanth.springbootproducerconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@PropertySource("classpath:topics.properties")
public class SpringbootProducerConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootProducerConsumerApplication.class, args);
	}

}
