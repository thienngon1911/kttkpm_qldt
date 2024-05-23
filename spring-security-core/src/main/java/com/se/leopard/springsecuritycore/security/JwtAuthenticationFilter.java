package com.se.leopard.springsecuritycore.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUltilities jwtUltilities;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String token = jwtUltilities.getToken(request);
			if(token != null && jwtUltilities.validateToken(token)) {
				String mssv = jwtUltilities.getMssvFromJwtToken(token);
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(mssv);
				if(userDetails != null) {
					UsernamePasswordAuthenticationToken authenticationUser = new UsernamePasswordAuthenticationToken(
							userDetails,
							null,
							userDetails.getAuthorities()
					);
					logger.info("User authenticated with mssv: " + mssv);
					authenticationUser.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticationUser);
				}
			}
		} catch (Exception e) {
			logger.error("`doFilterInternal` Cannot set user authentication: {}", e);
		}
		
		filterChain.doFilter(request, response);
	}

}