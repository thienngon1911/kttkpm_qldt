package com.se.leopard.serviceauthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceAuthenticationApplication.class, args);
	}

}
