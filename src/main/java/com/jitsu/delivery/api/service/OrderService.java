package com.jitsu.delivery.api.service;

import com.jitsu.delivery.api.exception.InvalidOrderException;
import com.jitsu.delivery.api.model.NominatimResponse;
import com.jitsu.delivery.api.model.Order;
import com.jitsu.delivery.api.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final ClientService clientService;
    private final OrderRepository orderRepository;

    public OrderService(ClientService clientService, OrderRepository orderRepository) {
        this.clientService = clientService;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {
        if (!isGeoCodingValid(order.getLatitude(), order.getLongitude())) {
            throw new InvalidOrderException("Geocoding fails for provided latitude and longitude.");
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

    boolean isGeoCodingValid(double latitude, double longitude) {
        // use Nominatim OpenStreetMap API to validate geocoding
        final RestTemplate restTemplate = new RestTemplate();
        String url = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("nominatim.openstreetmap.org")
                .path("/reverse")
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("format", "json")
                .build()
                .toUriString();
        try {
            NominatimResponse response = restTemplate.getForObject(url, NominatimResponse.class);
            return response != null && response.getDisplayName() != null;
        } catch (Exception e) {
            return false;
        }
    }
}
