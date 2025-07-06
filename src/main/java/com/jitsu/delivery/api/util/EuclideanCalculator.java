package com.jitsu.delivery.api.util;

import org.springframework.stereotype.Component;

@Component("euclidean")
public class EuclideanCalculator implements DistanceTimeCalculator {
    @Override
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double latDistance = (lat2 - lat1) * 69.0;
        double avgLat = (lat1 + lat2) / 2.0;
        double lonDistance = (lon2 - lon1) * 69.0 * Math.cos(Math.toRadians(avgLat));

        return Math.sqrt(latDistance * latDistance + lonDistance * lonDistance);
    }

    @Override
    public double calculateTime(double distance, double avgSpeed) {
        return distance/avgSpeed;
    }
}
