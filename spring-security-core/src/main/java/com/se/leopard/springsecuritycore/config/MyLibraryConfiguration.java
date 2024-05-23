package com.se.leopard.springsecuritycore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.se.leopard.springsecuritycore.security.CustomUserDetailsService;
import com.se.leopard.springsecuritycore.security.JwtAuthenticationFilter;
import com.se.leopard.springsecuritycore.security.JwtUltilities;

@Configuration
public class MyLibraryConfiguration {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
    	System.out.println("Start Bean JwtAuthenticationFilter--------------------");
        return new JwtAuthenticationFilter();
    }

    @Bean
    public JwtUltilities jwtUltilities() {
    	System.out.println("Start Bean JwtUltilities------------------");
        return new JwtUltilities();
    }

    @Bean
    public CustomUserDetailsService customUserDetailsService() {
    	System.out.println("Start Bean CustomUserDetailsService--------------------");
        return new CustomUserDetailsService();
    }
}