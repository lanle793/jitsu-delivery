package com.jitsu.delivery.api.service;

import com.jitsu.delivery.api.util.DistanceTimeCalculator;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DistanceTimeService {
    private final Map<String, DistanceTimeCalculator> strategies;

    public DistanceTimeService(Map<String, DistanceTimeCalculator> strategies) {
        this.strategies = strategies;
    }

    public double calculateDistance(String strategy, double lat1, double lon1, double lat2, double lon2) {
        DistanceTimeCalculator calculator = strategies.get(strategy.toLowerCase());
        return calculator.calculateDistance(lat1, lon1, lat2, lon2);
    }

    public double calculateTime(String strategy, double distance, double avgSpeed) {
        DistanceTimeCalculator calculator = strategies.get(strategy.toLowerCase());
        return calculator.calculateTime(distance, avgSpeed);
    }
}
