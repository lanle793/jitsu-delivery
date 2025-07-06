package com.jitsu.delivery.api.controller;

import com.jitsu.delivery.api.model.LowestCostCalculationRequest;
import com.jitsu.delivery.api.model.Route;
import com.jitsu.delivery.api.model.RouteInformation;
import com.jitsu.delivery.api.service.RouteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/routes")
public class RouteController {
    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping("/lowest")
    public Route getLowestCostRoute(@RequestBody LowestCostCalculationRequest request) {
        return routeService.getLowestCostRoute(request.getStrategy(),
                request.getOrderIds(),
                request.getWarehouseLatitude(),
                request.getWarehouseLongitude());
    }

    @GetMapping("/{routeId}")
    public RouteInformation getRoute(@PathVariable Long routeId) {
        return routeService.getRouteById(routeId);
    }
}
