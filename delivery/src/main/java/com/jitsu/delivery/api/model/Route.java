package com.jitsu.delivery.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "route")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routeId;
    private double cost;
    private double travelDistance;
    private double travelTime;

    public Route(double cost, double travelDistance, double travelTime) {
        this.cost = cost;
        this.travelDistance = travelDistance;
        this.travelTime = travelTime;
    }
}
