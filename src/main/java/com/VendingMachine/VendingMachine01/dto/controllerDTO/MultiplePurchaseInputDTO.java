package com.VendingMachine.VendingMachine01.dto.controllerDTO;
import java.util.List;

public class MultiplePurchaseInputDTO {
    private final List<Integer> productIds;
    private final List<Integer> quantities;
    private final List<Integer> prices;
    private final List<Integer> countsOfProduct;
    private final List<String> names;

    public MultiplePurchaseInputDTO(
            List<Integer> productIds,
            List<Integer> quantities,
            List<Integer> prices,
            List<Integer> countsOfProduct,
            List<String> names) {
        this.productIds = productIds;
        this.quantities = quantities;
        this.prices = prices;
        this.countsOfProduct = countsOfProduct;
        this.names = names;
    }

    // Getters

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
}
