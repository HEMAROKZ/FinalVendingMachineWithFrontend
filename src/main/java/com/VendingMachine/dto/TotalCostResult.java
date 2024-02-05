package com.VendingMachine.dto;

import java.util.List;

public class TotalCostResult {
    private final List<PurchaseInputDTO> responseList;
    private final int totalCost;

    public TotalCostResult(List<PurchaseInputDTO> responseList, int totalCost) {
        this.responseList = responseList;
        this.totalCost = totalCost;
    }

    public List<PurchaseInputDTO> getResponseList() {
        return responseList;
    }

    public int getTotalCost() {
        return totalCost;
    }


}

