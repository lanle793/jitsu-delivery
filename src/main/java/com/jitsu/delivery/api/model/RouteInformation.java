package com.jitsu.delivery.api.model;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class RouteInformation {
    Route route;
    List<Shipment> shipments;
}
