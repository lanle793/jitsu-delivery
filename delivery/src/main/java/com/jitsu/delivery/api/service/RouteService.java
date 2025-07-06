package com.jitsu.delivery.api.service;

import com.jitsu.delivery.api.model.Order;
import com.jitsu.delivery.api.model.Route;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {
    private final OrderService orderService;
    private final DistanceTimeService distanceTimeService;
    @Value("${delivery.avgSpeed:65}")
    private double avgSpeed;

    public RouteService(OrderService orderService, DistanceTimeService distanceTimeService) {
        this.orderService = orderService;
        this.distanceTimeService = distanceTimeService;
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
                lowestCostRoute = new Route(cost, distance, time);
            }
        }

        return lowestCostRoute;
    }

    double getRouteCost(double travelDistance, double travelTime) {
        return travelDistance * 18.75 + travelTime * 0.3;
    }

    public Route getRouteById(Long routeId) {
        return new Route();
    }
}
