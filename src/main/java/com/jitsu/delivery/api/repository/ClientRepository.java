package com.jitsu.delivery.api.repository;

import com.jitsu.delivery.api.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Order, Long> {
}
