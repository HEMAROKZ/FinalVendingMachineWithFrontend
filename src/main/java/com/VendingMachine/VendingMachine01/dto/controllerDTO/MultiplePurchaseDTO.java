package com.VendingMachine.VendingMachine01.dto.controllerDTO;

import java.util.List;

public class MultiplePurchaseDTO {
    private final int denomination50;
    private final int denomination20;
    private final int denomination10;
    private final int denomination5;
    private final int denomination2;
    private final int denomination1;
    private final List<Integer> productIds;
    private final List<Integer> quantities;
    private final List<Integer> prices;
    private final List<Integer> countsOfProduct;
    private final List<String> names;
    private final int totalCost;

    // Constructor to initialize the final fields
    public MultiplePurchaseDTO(
            int denomination50,
            int denomination20,
            int denomination10,
            int denomination5,
            int denomination2,
            int denomination1,
            List<Integer> productIds,
            List<Integer> quantities,
            List<Integer> prices,
            List<Integer> countsOfProduct,
            List<String> names,
            int totalCost) {
        this.denomination50 = denomination50;
        this.denomination20 = denomination20;
        this.denomination10 = denomination10;
        this.denomination5 = denomination5;
        this.denomination2 = denomination2;
        this.denomination1 = denomination1;
        this.productIds = productIds;
        this.quantities = quantities;
        this.prices = prices;
        this.countsOfProduct = countsOfProduct;
        this.names = names;
        this.totalCost = totalCost;
    }

    // Add getters for all the fields

    public int getDenomination50() {
        return denomination50;
    }

    public int getDenomination20() {
        return denomination20;
    }

    public int getDenomination10() {
        return denomination10;
    }

    public int getDenomination5() {
        return denomination5;
    }

    public int getDenomination2() {
        return denomination2;
    }

    public int getDenomination1() {
        return denomination1;
    }

    public List<Integer> getProductIds() {
        return productIds;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

    public List<Integer> getPrices() {
        return prices;
    }

    public List<Integer> getCountsOfProduct() {
        return countsOfProduct;
    }

    public List<String> getNames() {
        return names;
    }

    public int getTotalCost() {
        return totalCost;
    }
}

