package com.se.leopard.serviceapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceApigatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApigatewayApplication.class, args);
	}

}
