package com.jitsu.delivery.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jitsu.delivery.api.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
