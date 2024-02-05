package com.VendingMachine.dto;

import java.util.List;
import java.util.Map;

public class PurchaseResult {
    private final double change;
    private final Map<Integer, Integer> denominationMap;
    private final List<Map<String, Object>> items;

    @Override
    public String toString() {
        return "PurchaseResult{" +
                "change=" + change +
                ", denominationMap=" + denominationMap +
                ", items=" + items +
                '}';
    }

    public double getChange() {
        return change;
    }


    public Map<Integer, Integer> getDenominationMap() {
        return denominationMap;
    }


    public List<Map<String, Object>> getItems() {
        return items;
    }


    // Example constructor:
    public PurchaseResult(double change, Map<Integer, Integer> denominationMap, List<Map<String, Object>> items) {
        this.change = change;
        this.denominationMap = denominationMap;
        this.items = items;
    }
}
