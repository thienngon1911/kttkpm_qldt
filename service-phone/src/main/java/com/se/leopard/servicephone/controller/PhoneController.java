package com.se.leopard.servicephone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.leopard.servicephone.entity.Phone;
import com.se.leopard.servicephone.repository.PhoneRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/phone")
public class PhoneController {
	
	@Autowired
	private PhoneRepository phoneRepository;

	@GetMapping("/hello")
	public String helloWorld() {
		return "Hello World!";
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> getPhones() {
		return ResponseEntity.ok(phoneRepository.findAll());
	}
	
	@GetMapping("/generate")
	public ResponseEntity<?> generatePhones() {
		for(int i=1; i<=500; i++) {
			Phone p = Phone
					.builder()
					.id(i)
					.brand("Iphone")
					.model("Iphone 11 Pro")
					.price(369)
					.storage(128)
					.ram(4)
					.color("White")
					.operatingSystem("ios")
					.screenSize(8.5)
					.batteryCapacity(100)
					.cameraResolution(1080)
					.build();
			phoneRepository.save(p);
		}
		for(int i=501; i<=1000; i++) {
			Phone p = Phone
					.builder()
					.id(i)
					.brand("Oppo")
					.model("Oppo A20 Pro")
					.price(299)
					.storage(64)
					.ram(2)
					.color("Black")
					.operatingSystem("android")
					.screenSize(7.8)
					.batteryCapacity(100)
					.cameraResolution(1080)
					.build();
			phoneRepository.save(p);
		}
		for(int i=1001; i<=1500; i++) {
			Phone p = Phone
					.builder()
					.id(i)
					.brand("Samsung")
					.model("SamSung Galaxy A30S")
					.price(300)
					.storage(64)
					.ram(2)
					.color("Red")
					.operatingSystem("android")
					.screenSize(8.2)
					.batteryCapacity(100)
					.cameraResolution(1080)
					.build();
			phoneRepository.save(p);
		}
		for(int i=1501; i<=2000; i++) {
			Phone p = Phone
					.builder()
					.id(i)
					.brand("Xiaomi")
					.model("Xioao Redmi Note S11 5G")
					.price(420)
					.storage(128)
					.ram(5)
					.color("BlueComplex")
					.operatingSystem("android")
					.screenSize(7.5)
					.batteryCapacity(100)
					.cameraResolution(1080)
					.build();
			phoneRepository.save(p);
		}
		return ResponseEntity.ok("Ok!");
	}
}
