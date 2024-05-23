package com.se.leopard.servicephone.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.leopard.servicephone.entity.Phone;
import com.se.leopard.servicephone.repository.PhoneRepository;

import jakarta.validation.Valid;


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
	@Cacheable("phones")
	public List<Phone> getPhones() {
		return phoneRepository.findAll();
	}
	
	@PostMapping("/save")
	@CachePut("phones")
	public List<Phone> savePhone(@Valid @RequestBody Map<String, Object> requestBody) {
		String brand = requestBody.get("brand").toString();
	    String model = requestBody.get("model").toString();
	    double price = Double.parseDouble(requestBody.get("price").toString());
	    int stocks = Integer.parseInt(requestBody.get("stocks").toString());
	    int storage = Integer.parseInt(requestBody.get("storage").toString());;
	    int ram = Integer.parseInt(requestBody.get("ram").toString());;
	    String color = requestBody.get("color").toString();
	    String operatingSystem = requestBody.get("operatingSystem").toString();;
	    double screenSize = Double.parseDouble(requestBody.get("screenSize").toString());;
	    int batteryCapacity = Integer.parseInt(requestBody.get("batteryCapacity").toString());
	    int cameraResolution = Integer.parseInt(requestBody.get("cameraResolution").toString());
		try {
			Phone phone = Phone
					.builder()
					.brand(brand)
					.model(model)
					.price(price)
					.stocks(stocks)
					.storage(storage)
					.ram(ram)
					.color(color)
					.operatingSystem(operatingSystem)
					.screenSize(screenSize)
					.batteryCapacity(batteryCapacity)
					.cameraResolution(cameraResolution)
					.build();
			phoneRepository.save(phone);
			return phoneRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Phone>();
		}
	}
	
	@GetMapping("/generate")
	public ResponseEntity<?> generatePhones() {
		for(int i=2001; i<=5000; i++) {
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
//		for(int i=501; i<=1000; i++) {
//			Phone p = Phone
//					.builder()
//					.id(i)
//					.brand("Oppo")
//					.model("Oppo A20 Pro")
//					.price(299)
//					.storage(64)
//					.ram(2)
//					.color("Black")
//					.operatingSystem("android")
//					.screenSize(7.8)
//					.batteryCapacity(100)
//					.cameraResolution(1080)
//					.build();
//			phoneRepository.save(p);
//		}
//		for(int i=1001; i<=1500; i++) {
//			Phone p = Phone
//					.builder()
//					.id(i)
//					.brand("Samsung")
//					.model("SamSung Galaxy A30S")
//					.price(300)
//					.storage(64)
//					.ram(2)
//					.color("Red")
//					.operatingSystem("android")
//					.screenSize(8.2)
//					.batteryCapacity(100)
//					.cameraResolution(1080)
//					.build();
//			phoneRepository.save(p);
//		}
//		for(int i=1501; i<=2000; i++) {
//			Phone p = Phone
//					.builder()
//					.id(i)
//					.brand("Xiaomi")
//					.model("Xioao Redmi Note S11 5G")
//					.price(420)
//					.storage(128)
//					.ram(5)
//					.color("BlueComplex")
//					.operatingSystem("android")
//					.screenSize(7.5)
//					.batteryCapacity(100)
//					.cameraResolution(1080)
//					.build();
//			phoneRepository.save(p);
//		}
		return ResponseEntity.ok("Ok!");
	}
}
