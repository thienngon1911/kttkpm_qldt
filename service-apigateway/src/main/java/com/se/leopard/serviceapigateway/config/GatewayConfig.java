package com.se.leopard.serviceapigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
	
	@Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
        		.route("service_authentication", r -> r.path("/authentication/**")
                        .uri("http://service-authentication:8081"))
                .route("service_phone", r -> r.path("/phone/**")
                        .uri("http://service-phone:8082"))
                .route("service_order", r -> r.path("/order/**")
                        .uri("http://service-order:8083"))
                .route("serivce_rest-template", r -> r.path("/rest-template/**")
                        .uri("http://rest-template-resilience4j:8089"))
                .build();
    }
}
