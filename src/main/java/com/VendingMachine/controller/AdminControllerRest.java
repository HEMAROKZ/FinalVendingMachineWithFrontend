package com.VendingMachine.controller;

import com.VendingMachine.dto.InventoryDTO;
import com.VendingMachine.model.Inventry;
import com.VendingMachine.service.AdminServices;
import com.VendingMachine.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminControllerRest {
    private final AdminServices adminServices;
    private final InventoryService inventoryService;

    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);


    public AdminControllerRest(final AdminServices adminServices, final InventoryService inventoryService) {
        this.adminServices = adminServices;
        this.inventoryService = inventoryService;
    }



    @PostMapping("/products")
    @Operation(summary = "ADMIN PROCESS--Add  Inventory item ")
    public String saveInventory(@RequestBody InventoryDTO e) {
        return adminServices.saveInventory(e)+" product added successfully";
    }

    @PutMapping("/productsRest")
    @Operation(summary = "ADMIN PROCESS--Update  Inventory item ")
    public String updateInventory(@RequestBody Inventry e) {
        return adminServices.updateInventory(e)+" Product (s) updated successfully";
    }

    @DeleteMapping("/deleteRest/{id}")
    @Operation(summary = "ADMIN PROCESS--DELETE Inventory item")
    public ResponseEntity<String> deleteProductById(@PathVariable final int id) {
        int deletionResult = adminServices.deleteProductById(id);

        if (deletionResult==1) {
            return ResponseEntity.ok("Product deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Product not found"); // or use a different status code
        }
    }


}
