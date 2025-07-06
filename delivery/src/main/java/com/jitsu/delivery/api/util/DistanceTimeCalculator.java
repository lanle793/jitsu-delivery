package com.jitsu.delivery.api.util;

public interface DistanceTimeCalculator {
    double calculateDistance(double lat1, double lon1, double lat2, double lon2);
    double calculateTime(double distance, double avgSpeed);
}
