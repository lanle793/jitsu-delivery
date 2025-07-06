package com.jitsu.delivery.api.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EuclideanCalculatorTest {
    private final EuclideanCalculator euclideanCalculator = new EuclideanCalculator();

    @Test
    void calculateDistance() {
        assertEquals(1080.9015441498289, euclideanCalculator.calculateDistance(12.1, 25.1, 23.2, 13.5));
    }
}