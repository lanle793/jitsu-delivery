package com.jitsu.delivery.api.service;

import com.jitsu.delivery.api.exception.RouteNotFoundException;
import com.jitsu.delivery.api.model.Order;
import com.jitsu.delivery.api.model.Route;
import com.jitsu.delivery.api.model.RouteInformation;
import com.jitsu.delivery.api.model.Shipment;
import com.jitsu.delivery.api.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {
    private final OrderService orderService;
    private final DistanceTimeService distanceTimeService;
    private final RouteRepository routeRepository;
    @Value("${delivery.avgSpeed:65}")
    private double avgSpeed;

    public RouteService(OrderService orderService, DistanceTimeService distanceTimeService, RouteRepository routeRepository) {
        this.orderService = orderService;
        this.distanceTimeService = distanceTimeService;
        this.routeRepository = routeRepository;
    }

    public Route getLowestCostRoute(String strategy, List<Long> orderIds,
                                    double warehouseLatitude, double warehouseLongitude) {
        Route lowestCostRoute = null;
        double lowestCost = Double.MAX_VALUE;
        List<Order> orders = orderService.getOrdersByIds(orderIds);

        for (Order order: orders) {
            double destinationLatitude = order.getLatitude();
            double destinationLongitude = order.getLongitude();
            double distance = distanceTimeService.calculateDistance(strategy,
                    destinationLatitude, destinationLongitude, warehouseLatitude, warehouseLongitude);
            double time = distanceTimeService.calculateTime(strategy, distance, avgSpeed);
            double cost = getRouteCost(distance, time);
            if (cost < lowestCost) {
                lowestCost = cost;
                lowestCostRoute = new Route(strategy, cost, distance, time, warehouseLatitude, warehouseLongitude);
            }
        }

        return lowestCostRoute;
    }

    double getRouteCost(double travelDistance, double travelTime) {
        return travelDistance * 18.75 + travelTime * 0.3;
    }

    public RouteInformation getRouteById(Long routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RouteNotFoundException(routeId));
        List<Shipment> shipments = route.getShipments();
        return new RouteInformation(route, shipments);
    }
}
