package com.jitsu.delivery.api.service;

import com.jitsu.delivery.api.model.Order;
import com.jitsu.delivery.api.model.Route;
import com.jitsu.delivery.api.model.Shipment;
import com.jitsu.delivery.api.repository.RouteRepository;
import com.jitsu.delivery.api.util.DistanceTimeCalculator;
import com.jitsu.delivery.api.util.EuclideanCalculator;
import com.jitsu.delivery.api.util.HaversineCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RouteServiceTest {
    @Mock
    private OrderService orderService;
    private DistanceTimeService distanceTimeService;
    @Mock
    private AddressService addressService;
    @Mock
    private RouteRepository routeRepository;

    private RouteService routeService;

    @BeforeEach
    void setup() {
        Map<String, DistanceTimeCalculator> strategies = new HashMap<>();
        strategies.put("haversine", new HaversineCalculator());
        strategies.put("euclidean", new EuclideanCalculator());
        distanceTimeService = new DistanceTimeService(strategies);
        routeService = new RouteService(orderService, distanceTimeService,
                addressService, routeRepository, 65);
    }

    @Test
    void getLowestCostRoute() {
        String strategy = "haversine";
        List<Long> orderIds = List.of(123L, 124L);
        Order order1 = new Order(123L, 12.1, 24.8, 45L,
                Instant.now(), Instant.now(), "US", new Shipment());
        Order order2 = new Order(124L, 14.1, 25.1, 45L,
                Instant.now(), Instant.now(), "US", new Shipment());
        List<Order> orderList = List.of(order1, order2);
        when(addressService.isGeoCodingValid(23.2, 13.5)).thenReturn(true);
        when(orderService.getOrdersByIds(orderIds)).thenReturn(orderList);

        Route lowestCostRoute = routeService.getLowestCostRoute(strategy, orderIds,
                23.2, 13.5);

        assertEquals(new Route(strategy, 18473.046202987774, 984.9866725681516,
                        15.153641116433102, 23.2, 13.5),
                lowestCostRoute);
    }
}