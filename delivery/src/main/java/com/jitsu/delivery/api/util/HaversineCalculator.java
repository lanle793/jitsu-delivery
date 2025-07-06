package com.jitsu.delivery.api.util;

import org.springframework.stereotype.Component;

@Component("haversine")
public class HaversineCalculator implements DistanceTimeCalculator {
    private static final double EARTH_RADIUS_MILES = 3958.8;

    @Override
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_MILES * c;
    }

    @Override
    public double calculateTime(double distance, double avgSpeed) {
        return distance/avgSpeed;
    }
}
