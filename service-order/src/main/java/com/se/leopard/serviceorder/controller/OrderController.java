package com.se.leopard.serviceorder.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.leopard.serviceorder.entity.Order;
import com.se.leopard.serviceorder.entity.Phone;
import com.se.leopard.serviceorder.entity.User;
import com.se.leopard.serviceorder.repository.OrderRepository;
import com.se.leopard.serviceorder.repository.UserRepository;

import jakarta.validation.Valid;

import com.se.leopard.serviceorder.repository.PhoneRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PhoneRepository phoneRepository;

	@PostMapping("/order")
	public ResponseEntity<?> order(@Valid @RequestBody Map<String, Object> requestBody) {
		
		long userId = Long.parseLong(requestBody.get("userId").toString());
		long phoneId = Long.parseLong(requestBody.get("phoneId").toString());
		
		User user = userRepository.findById(userId).orElse(null);
		Phone phone = phoneRepository.findById(phoneId).orElse(null);
		Order order = Order
				.builder()
				.user(user)
				.phone(phone)
				.build();
		try {
			Order orderSaved = orderRepository.save(order);
			return ResponseEntity.ok(orderSaved);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello World!";
	}
}
