package com.VendingMachine.service;

import com.VendingMachine.customeexception.ProductAlreadyExist;
import com.VendingMachine.customeexception.ProductIdNotFoundException;
import com.VendingMachine.customeexception.ProductUnavialableException;
import com.VendingMachine.dao.InitialBalanceDAOImp;
import com.VendingMachine.dao.InventoryDAOImp;
import com.VendingMachine.dto.InventoryDTO;
import com.VendingMachine.model.InitialBalanceAndPurchaseHistory;
import com.VendingMachine.model.Inventry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServices {

    private final InventoryDAOImp repository;
    private final InitialBalanceDAOImp initialBalanceDAOImp;

    private static final Logger log = LoggerFactory.getLogger(AdminServices.class);

    public AdminServices(final InventoryDAOImp repository, final InitialBalanceDAOImp initialBalanceDAOImp) {
        this.repository = repository;
        this.initialBalanceDAOImp = initialBalanceDAOImp;
    }


////////////////////////

    public int saveInventory(final InventoryDTO inventry) {
        int productId = inventry.getProductId();

        if (productId != 0) {
            log.info("Inside update inventory: {}", inventry);
            log.warn("product id == "+productId);
            List<Inventry> existingInventory = repository.findById(productId);
            log.info("Inside update existingInventory: {}", existingInventory);

            if (existingInventory.isEmpty()) {
                return repository.save(inventry);
            }
            else {
                throw new ProductAlreadyExist(" product ID : {}  Already Exist in the inventory try with new product ID: " + productId);
            }
        } else {
            throw new ProductIdNotFoundException("Invalid product ID given");
        }
    }

    ////////////////////////////////////////
    public int updateInventory(final Inventry inventry) {
        int productId = inventry.getProductId();

        if (productId != 0) {
            log.info("Inside update inventory: {}", inventry);
log.warn("product id == "+productId);
            List<Inventry> existingInventory = repository.findById(productId);
            log.info("Inside update existingInventory: {}", existingInventory);

            if (existingInventory.isEmpty()) {
                throw new ProductUnavialableException("Invalid product ID provided for update: " + productId);
            }

            return repository.update(inventry);
        } else {
            throw new ProductIdNotFoundException("Invalid product ID given");
        }
    }


    //    public void deleteProductById(final int productId) {
//        repository.deleteById(productId);
//    }
    public int deleteProductById(int productId){
       List<Inventry> value= repository.findById(productId);
       log.info("value=="+value);
        if (value.isEmpty()) {
            throw new ProductUnavialableException("Invalid product ID provided for Delete: " + productId);
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
