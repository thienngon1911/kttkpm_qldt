package com.se.leopard.servicephone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
public class ServicePhoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicePhoneApplication.class, args);
	}

}
