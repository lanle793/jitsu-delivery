package com.jitsu.delivery.api.util;

import org.springframework.stereotype.Component;

@Component("google")
public class GoogleCalculator implements DistanceTimeCalculator {
    // We are only mocking the results here so return random numbers
    @Override
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        return Math.random();
    }

    @Override
    public double calculateTime(double distance, double avgSpeed) {
        return Math.random();
    }
}
