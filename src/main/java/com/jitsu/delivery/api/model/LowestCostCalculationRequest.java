package com.jitsu.delivery.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LowestCostCalculationRequest {
    private String strategy;
    private List<Long> orderIds;
    private double warehouseLatitude;
    private double warehouseLongitude;
}
