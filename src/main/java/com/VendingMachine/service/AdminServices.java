package com.VendingMachine.service;

import com.VendingMachine.customeexception.ProductUnavialableException;
import com.VendingMachine.dao.InitialBalanceDAOImp;
import com.VendingMachine.dao.InventoryDAOImp;
import com.VendingMachine.dto.InventoryDTO;
import com.VendingMachine.model.InitialBalanceAndPurchaseHistory;
import com.VendingMachine.model.Inventry;
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

//    public void saveInventory(final InventoryDTO inventry) {
//        repository.save(inventry);
//    }
////////////////////////

    public String saveInventory(final InventoryDTO inventry) {
        return repository.save(inventry)+"saved sucessfull";
    }

    ////////////////////////////////////////
    public int updateInventory(final Inventry inventry) {
        return repository.update(inventry);
    }

//    public void deleteProductById(final int productId) {
//        repository.deleteById(productId);
//    }
    public int deleteProductById(int productId){

        if (repository.findById(productId).isEmpty()) {
            throw new ProductUnavialableException("Invalid product ID provided for update: " + productId);
        }
        return repository.deleteById(productId);
    }
    public List<InitialBalanceAndPurchaseHistory> getListOfAllPurchaseHistory() {
        return allProductToPurchaseHistory(initialBalanceDAOImp.getAllPurchaseHistory());
    }

    public List<InitialBalanceAndPurchaseHistory> allProductToPurchaseHistory(final List<InitialBalanceAndPurchaseHistory> allInvProduct) {
        return allInvProduct.stream().map(this::productToUserProductHistory).collect(Collectors.toList());
    }
//
    public  InitialBalanceAndPurchaseHistory productToUserProductHistory(final InitialBalanceAndPurchaseHistory inventory) {
        return new InitialBalanceAndPurchaseHistory(
                inventory.getId(),
                inventory.getOrder_id(),
                inventory.getOrder_time(),
                inventory.getCustomerInputAmount(),
                inventory.getBalanceAmount(),
                inventory.getVendingMachineBalance()
        );
    }
}
