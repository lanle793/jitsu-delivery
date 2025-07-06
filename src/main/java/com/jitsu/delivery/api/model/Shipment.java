package com.jitsu.delivery.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shipment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipmentId;

    @ManyToOne
    @JoinColumn(name = "route_id")
    @JsonBackReference
    private Route route;

    @OneToOne
    @JoinColumn(name = "order_id", unique = true)
    private Order order;

    private double travelDistance;
    private double travelTime;
}
