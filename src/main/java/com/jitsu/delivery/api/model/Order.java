package com.jitsu.delivery.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private double latitude;
    private double longitude;
    private Long clientId;
    private Instant dropoffStart;
    private Instant dropoffEnd;
    private String regionCode;

    @OneToOne(mappedBy = "order")
    private Shipment shipment;
}
