package com.VendingMachine.VendingMachine01.dto;

import java.util.List;
import java.util.Map;

public class PurchaseResult {
    private double change;
    private Map<Integer, Integer> denominationMap;
    private List<Map<String, Object>> items;

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

    public void setChange(double change) {
        this.change = change;
    }

    public Map<Integer, Integer> getDenominationMap() {
        return denominationMap;
    }

    public void setDenominationMap(Map<Integer, Integer> denominationMap) {
        this.denominationMap = denominationMap;
    }

    public List<Map<String, Object>> getItems() {
        return items;
    }

    public void setItems(List<Map<String, Object>> items) {
        this.items = items;
    }

    // Example constructor:
    public PurchaseResult(double change, Map<Integer, Integer> denominationMap, List<Map<String, Object>> items) {
        this.change = change;
        this.denominationMap = denominationMap;
        this.items = items;
    }
}
