package com.se.leopard.servicephone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.se.leopard.servicephone.entity.Phone;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
}
