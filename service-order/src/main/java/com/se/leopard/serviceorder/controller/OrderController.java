package com.se.leopard.serviceorder.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/order")
public class OrderController {

	@GetMapping("/")
	public String helloWorld() {
		return "Hello World 2!";
	}
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello World!";
	}
}
