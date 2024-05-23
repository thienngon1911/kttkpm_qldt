package com.se.leopard.servicephone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServicePhoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicePhoneApplication.class, args);
	}

}
