package com.se.leopard.rest_template_resilience4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RestTemplateResilience4jApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestTemplateResilience4jApplication.class, args);
	}

}
