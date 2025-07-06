package com.jitsu.delivery.api.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HaversineCalculatorTest {
    private final HaversineCalculator haversineCalculator = new HaversineCalculator();

    @Test
    void calculateDistance() {
        assertEquals(1081.1888991096132, haversineCalculator.calculateDistance(12.1, 25.1, 23.2, 13.5));
    }
}