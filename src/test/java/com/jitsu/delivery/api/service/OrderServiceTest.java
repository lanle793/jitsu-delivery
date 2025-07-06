package com.jitsu.delivery.api.service;

import com.jitsu.delivery.api.exception.InvalidOrderException;
import com.jitsu.delivery.api.model.Order;
import com.jitsu.delivery.api.model.Shipment;
import com.jitsu.delivery.api.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private ClientService clientService;
    @Mock
    private AddressService addressService;
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderService orderService;

    @Test
    void createOrder() {
        Order order = new Order(123L, 12.1, 24.8, 45L, Instant.now(), Instant.now(), "US", new Shipment());
        when(addressService.isGeoCodingValid(12.1, 24.8)).thenReturn(true);
        when(addressService.isValidRegionCode("US")).thenReturn(true);
        when(clientService.clientExists(45L)).thenReturn(true);

        orderService.createOrder(order);

        verify(orderRepository).save(order);
    }

    @Test
    void createOrder_invalidGeocoding_throwException() {
        Order order = new Order(123L, 12.1, 24.8, 45L, Instant.now(), Instant.now(), "US", new Shipment());
        when(addressService.isGeoCodingValid(12.1, 24.8)).thenReturn(false);

        assertThrows(InvalidOrderException.class, () -> orderService.createOrder(order));
    }

    @Test
    void createOrder_invalidRegionCode_throwException() {
        Order order = new Order(123L, 12.1, 24.8, 45L, Instant.now(), Instant.now(), "US", new Shipment());
        when(addressService.isGeoCodingValid(12.1, 24.8)).thenReturn(true);
        when(addressService.isValidRegionCode("US")).thenReturn(false);

        assertThrows(InvalidOrderException.class, () -> orderService.createOrder(order));
    }

    @Test
    void createOrder_noClientExists_throwException() {
        Order order = new Order(123L, 12.1, 24.8, 45L, Instant.now(), Instant.now(), "US", new Shipment());
        when(addressService.isGeoCodingValid(12.1, 24.8)).thenReturn(true);
        when(addressService.isValidRegionCode("US")).thenReturn(true);
        when(clientService.clientExists(45L)).thenReturn(false);

        assertThrows(InvalidOrderException.class, () -> orderService.createOrder(order));
    }
}