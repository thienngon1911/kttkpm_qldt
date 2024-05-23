package com.se.leopard.rest_template_resilience4j.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.se.leopard.rest_template_resilience4j.entity.Phone;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest-template")
public class RestController {
	
	private final String SERVICE_PHONE = "http://localhost:8082/phone";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/hello")
	public String helloWorld() {
		return "Hello world!";
	}
	
	@GetMapping("/getPhones")
	public List<Phone> getPhones(@RequestHeader("Authorization") String authorizationHeader) {
		HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);
        
		ResponseEntity<List<Phone>> responseEntity = restTemplate.exchange(
				SERVICE_PHONE + "/list",
				HttpMethod.GET,
				new HttpEntity<>(headers),
				new ParameterizedTypeReference<List<Phone>>() {}
			);
		return responseEntity.getBody();
	}
}
