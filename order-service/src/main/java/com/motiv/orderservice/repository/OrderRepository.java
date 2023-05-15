package com.motiv.orderservice.repository;

import com.motiv.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

// Long is primary key
public interface OrderRepository extends JpaRepository<Order, Long> {
}
