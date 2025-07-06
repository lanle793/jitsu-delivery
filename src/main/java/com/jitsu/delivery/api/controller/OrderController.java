package com.jitsu.delivery.api.controller;

import com.jitsu.delivery.api.model.Order;
import com.jitsu.delivery.api.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order submitOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable final Long orderId) {
        return orderService.getOrderById(orderId);
    }
}
