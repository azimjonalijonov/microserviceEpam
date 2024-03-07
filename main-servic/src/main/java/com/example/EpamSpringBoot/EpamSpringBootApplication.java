package com.example.EpamSpringBoot;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
@EnableDiscoveryClient

public class EpamSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(EpamSpringBootApplication.class, args);
	}

}
