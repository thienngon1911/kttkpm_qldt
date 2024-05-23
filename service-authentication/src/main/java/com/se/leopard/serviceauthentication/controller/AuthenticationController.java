package com.se.leopard.serviceauthentication.controller;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.leopard.serviceauthentication.entity.Role;
import com.se.leopard.serviceauthentication.entity.User;
import com.se.leopard.serviceauthentication.enumric.RoleName;
import com.se.leopard.serviceauthentication.repository.UserRepository;
import com.se.leopard.serviceauthentication.security.JwtUltilities;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtUltilities jwtUltilities;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@GetMapping("/")
	public String helloWorld() {
		return "Hello world!";
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody Map<String, Object> requestBody) {
		String username = requestBody.get("username").toString();
		String password = requestBody.get("password").toString();
		
		Optional<User> byUsername = userRepository.findByUsername(username);
		
		if (byUsername.isPresent()) {
            throw new RuntimeException("Username already exists");
        }
		
		String encodedPassword = passwordEncoder.encode(password);
		
		Role role = Role
			.builder()
			.id(1)
			.roleName(RoleName.USER)
			.build();
		
		User user = User
			.builder()
			.username(username)
			.password(encodedPassword)
			.role(role)
			.build();
		
		try {
			User u = userRepository.save(user);
			return ResponseEntity.ok(u);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody Map<String, Object> requestBody) {
		try {
			String username = requestBody.get("username").toString();
			String password = requestBody.get("password").toString();
			
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			System.out.println("authorities=" + authorities);
			if(authorities.isEmpty()) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unrole user access");
			}
			Object[] userRoles = authorities.toArray();
			String userRole = userRoles[0].toString();
			RoleName userRoleName = RoleName.valueOf(userRole);
			
			User principalForceToUser = (User) authentication.getPrincipal();
			System.out.println("principalForceToUser=" + principalForceToUser.getId());
			
			Optional<User> theUser = userRepository.findById(principalForceToUser.getId());
			if(!theUser.isPresent()) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not find the user!");
			}
			
			String token = jwtUltilities.generateToken(username, userRoleName);
			return ResponseEntity.ok(token);
		} catch (BadCredentialsException e) {
			return ResponseEntity.badRequest().body("Wrong username or password!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}
