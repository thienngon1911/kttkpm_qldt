package com.se.leopard.serviceorder.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.se.leopard.serviceorder.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public Optional<User> findByUsername(String username);
	
	@Query(value = "SELECT * FROM user WHERE username = ?1 AND role_id = ?2", nativeQuery = true)
	public User findByUsernameAndRole(String username, long roleId); 
}