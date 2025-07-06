package com.jitsu.delivery.api.service;

import com.jitsu.delivery.api.exception.InvalidOrderException;
import com.jitsu.delivery.api.model.Order;
import com.jitsu.delivery.api.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final ClientService clientService;
    private final AddressService addressService;
    private final OrderRepository orderRepository;

    public OrderService(ClientService clientService, AddressService addressService, OrderRepository orderRepository) {
        this.clientService = clientService;
        this.addressService = addressService;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {
        if (!addressService.isGeoCodingValid(order.getLatitude(), order.getLongitude())) {
            throw new InvalidOrderException("Geocoding fails for provided latitude and longitude.");
        }

        if (!addressService.isValidRegionCode(order.getRegionCode())) {
            throw new InvalidOrderException("Provided region code is not valid.");
        }

        Long clientId = order.getClientId();
        if (!clientService.clientExists(clientId)) {
            throw new InvalidOrderException("The client with ID: " + clientId + " does not exist.");
        }

        return orderRepository.save(order);
    }

    public Order getOrderById(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        return optionalOrder.orElse(null);
    }

    public List<Order> getOrdersByIds(List<Long> orderIds) {
        return orderRepository.findAllById(orderIds);
    }
}
