package com.se.leopard.serviceorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.se.leopard.serviceorder.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
