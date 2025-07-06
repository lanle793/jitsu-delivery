package com.jitsu.delivery.api.service;

import com.jitsu.delivery.api.exception.CostCalculationException;
import com.jitsu.delivery.api.exception.RouteNotFoundException;
import com.jitsu.delivery.api.model.Order;
import com.jitsu.delivery.api.model.Route;
import com.jitsu.delivery.api.model.RouteInformation;
import com.jitsu.delivery.api.model.Shipment;
import com.jitsu.delivery.api.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;

@Service
public class RouteService {
    private final OrderService orderService;
    private final DistanceTimeService distanceTimeService;
    private final AddressService addressService;
    private final RouteRepository routeRepository;
    private final double avgSpeed;

    public RouteService(OrderService orderService, DistanceTimeService distanceTimeService, AddressService addressService,
                        RouteRepository routeRepository, @Value("${delivery.avgSpeed:65}") double avgSpeed) {
        this.orderService = orderService;
        this.distanceTimeService = distanceTimeService;
        this.addressService = addressService;
        this.routeRepository = routeRepository;
        this.avgSpeed = avgSpeed;
    }

    public Route getLowestCostRoute(String strategy, List<Long> orderIds,
                                    double warehouseLatitude, double warehouseLongitude) {
        if (!addressService.isGeoCodingValid(warehouseLatitude, warehouseLongitude)) {
            throw new CostCalculationException("Geocoding fails for provided warehouse latitude and longitude.");
        }

        Route lowestCostRoute = null;
        double lowestCost = Double.MAX_VALUE;
        List<Order> orders = orderService.getOrdersByIds(orderIds);

        if (CollectionUtils.isEmpty(orders)) {
            throw new CostCalculationException("Fail to retrieve order list for calculation.");
        }

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

    private double getRouteCost(double travelDistance, double travelTime) {
        return travelDistance * 18.75 + travelTime * 0.3;
    }

    public RouteInformation getRouteById(Long routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RouteNotFoundException(routeId));
        List<Shipment> shipments = route.getShipments();
        shipments.sort(Comparator.comparing(Shipment::getTravelDistance));
        return new RouteInformation(route, shipments);
    }
}
