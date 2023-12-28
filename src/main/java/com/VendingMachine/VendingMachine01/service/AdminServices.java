package com.VendingMachine.VendingMachine01.service;

import com.VendingMachine.VendingMachine01.dao.InitialBalanceDAOImp;
import com.VendingMachine.VendingMachine01.dao.InventoryDAOImp;
import com.VendingMachine.VendingMachine01.dto.InventoryDTO;
import com.VendingMachine.VendingMachine01.model.InitialBalanceAndPurchaseHistory;
import com.VendingMachine.VendingMachine01.model.Inventry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServices {

    private final InventoryDAOImp repository;
    private final InitialBalanceDAOImp initialBalanceDAOImp;

    public AdminServices(final InventoryDAOImp repository, final InitialBalanceDAOImp initialBalanceDAOImp) {
        this.repository = repository;
        this.initialBalanceDAOImp = initialBalanceDAOImp;
    }

    public void saveInventory(final InventoryDTO inventry) {
        repository.save(inventry);
    }

    public int updateInventory(final Inventry inventry) {
        return repository.update(inventry);
    }

    public void deleteProductById(final int productId) {
        repository.deleteById(productId);
    }

    public List<InitialBalanceAndPurchaseHistory> getListOfAllPurchaseHistory() {
        return allProductToPurchaseHistory(initialBalanceDAOImp.getAllPurchaseHistory());
    }

    public List<InitialBalanceAndPurchaseHistory> allProductToPurchaseHistory(final List<InitialBalanceAndPurchaseHistory> allInvProduct) {
        return allInvProduct.stream().map(this::productToUserProductHistory).collect(Collectors.toList());
    }

    public InitialBalanceAndPurchaseHistory productToUserProductHistory(final InitialBalanceAndPurchaseHistory inventory) {
        return new InitialBalanceAndPurchaseHistory(
                inventory.getId(),
                inventory.getProductId(),
                inventory.getProduct(),
                inventory.getProductPrice(),
                inventory.getCustomerInputAmount(),
                inventory.getVendingMachineBalance(),
                inventory.getInitialBalance()
        );
    }
}
