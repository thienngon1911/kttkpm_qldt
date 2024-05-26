package com.se.leopard.rest_template_resilience4j.controller;

import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest-template")
public class RestController {
	
	private final String SERVICE_PHONE = "http://service-phone:8082/phone";
	private int ATTEMP_RETRY = 1;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/hello")
	public String helloWorld() {
		return "Hello world!";
	}
	
	@GetMapping("/getPhones")
//	@CircuitBreaker(name = "CIRCUIT_BREAKER_1", fallbackMethod = "fallbackCircuitGetPhones")
//	@Retry(name = "RETRY_1", fallbackMethod = "fallbackRetryGetPhones")
//	@RateLimiter(name = "RATE_LIMITER_1", fallbackMethod = "fallbackRateLimiterGetPhones")
	public List<Phone> getPhones(@RequestHeader("Authorization") String authorizationHeader) {
//		System.out.println("\n\nMethod findAllPlant() retry " + ATTEMP_RETRY++ + " times at " + LocalDateTime.now() + "\n\n"); // retry
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
	
	@GetMapping("/simulateTimeLimiter")
	@TimeLimiter(name = "TIME_LIMITER_1", fallbackMethod = "fallbackTimeLimiter")
	public CompletableFuture<List<Phone>> testTimeLimiter(@RequestHeader("Authorization") String authorizationHeader) {
		return CompletableFuture.supplyAsync(() -> {
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", authorizationHeader);

                // Giả lập độ trễ 3 giây
                TimeUnit.SECONDS.sleep(3);

                ResponseEntity<List<Phone>> responseEntity = restTemplate.exchange(
                        SERVICE_PHONE + "/list",
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<List<Phone>>() {}
                );

                return responseEntity.getBody();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
	}

	// FALLBACK METHODS
	public List<Phone> fallbackCircuitGetPhones(String authorizationHeader, Throwable throwable) {
		return Stream.of(
				new Phone(0, "Circuit Breaker", "Make in trouble in", 0, 0, 0, 0, "The microservice you call is now dead.", "Circuit breaker is now open.", 0, 0, 0)
		).collect(Collectors.toList());
    }
	
	public List<Phone> fallbackRetryGetPhones(String authorizationHeader, Throwable throwable) {
		return Stream.of(
				new Phone(0, "Retry", "Retry", 0, 0, 0, 0, "The microservice " + SERVICE_PHONE + " was not response: " + ATTEMP_RETRY + " times", "Retry Exception.", 0, 0, 0)
		).collect(Collectors.toList());
    }
	
	public List<Phone> fallbackRateLimiterGetPhones(String authorizationHeader, Throwable throwable) {
		return Stream.of(
				new Phone(0, "Rate Limiter", "Please don't spam call data", 0, 0, 0, 0, "Too much requests.", "Please slow down!", 0, 0, 0)
		).collect(Collectors.toList());
    }
	public CompletableFuture<List<Phone>> fallbackTimeLimiter(String authorizationHeader, Throwable throwable) {
        return CompletableFuture.completedFuture(List.of(
        		new Phone(0, "Time Limiter", "Sorry, server is now bussy!", 0, 0, 0, 0, "Your request took to long response.", "Please retry in minutes!", 0, 0, 0)
        ));
    }
}
