package com.se.leopard.servicephone.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.se.leopard.springsecuritycore.security.CustomAccessDeniedHandler;
import com.se.leopard.springsecuritycore.security.CustomAuthenticationEntryPoint;
import com.se.leopard.springsecuritycore.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean // Cấu hình securityFilterChain lọc http request
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
			.csrf(csrf -> csrf.disable())
			.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.exceptionHandling(exception -> exception
		            .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
		            .accessDeniedHandler(new CustomAccessDeniedHandler())
		        )
			.authorizeHttpRequests(auth -> auth
				
//				.requestMatchers("/**").permitAll()
				.requestMatchers("/phone/**").permitAll()
//				.requestMatchers("/student/**").hasAuthority("STUDENT")
//				.requestMatchers("/student/**").permitAll()
//				.requestMatchers("/teacher/**").hasAuthority("TEACHER")
//				.requestMatchers("/manager/**").permitAll()
				.anyRequest().authenticated()
			).httpBasic(Customizer.withDefaults())
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.build();
	}
	
	@Bean // Khởi tạo get PasswordEncoder để lát gọi mã hóa password
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean // Khởi tạo get AuthenticationManager để quản lý authentication
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
}