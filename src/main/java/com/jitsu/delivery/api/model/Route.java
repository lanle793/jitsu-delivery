package com.jitsu.delivery.api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "route")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routeId;
    private String strategy;
    private double cost;
    private double travelDistance;
    private double travelTime;
    private double warehouseLatitude;
    private double warehouseLongitude;

    @OneToMany(mappedBy = "route", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Shipment> shipments = new ArrayList<>();

    public Route(String strategy, double cost, double travelDistance, double travelTime,
                 double warehouseLatitude, double warehouseLongitude) {
        this.strategy = strategy;
        this.cost = cost;
        this.travelDistance = travelDistance;
        this.travelTime = travelTime;
        this.warehouseLatitude = warehouseLatitude;
        this.warehouseLongitude = warehouseLongitude;
    }
}
