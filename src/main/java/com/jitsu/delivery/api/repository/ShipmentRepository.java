package com.jitsu.delivery.api.repository;

import com.jitsu.delivery.api.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}
