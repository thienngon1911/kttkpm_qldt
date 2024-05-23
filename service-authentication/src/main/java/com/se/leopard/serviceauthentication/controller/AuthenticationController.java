package com.se.leopard.serviceauthentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

	@GetMapping("/hello")
	public String helloWorld() {
		return "Hello World!";
	}
	
}
