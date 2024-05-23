package com.se.leopard.servicephone.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/phone")
public class PhoneController {

	@GetMapping("/hello")
	public String helloWorld() {
		return "Hello World!";
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getPhones() {
		
		return ResponseEntity.ok(null);
	}
}
