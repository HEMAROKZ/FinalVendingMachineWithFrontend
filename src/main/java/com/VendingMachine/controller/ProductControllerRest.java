package com.VendingMachine.controller;

import com.VendingMachine.dto.InventoryDTO;
import com.VendingMachine.service.DenominationService;
import com.VendingMachine.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import com.VendingMachine.model.Inventry;
import com.VendingMachine.util.FileUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin()
public class ProductControllerRest {

    private final InventoryService inventoryService;
    private final DenominationService denominationService;
    @Autowired
    FileUtility fileUtility;

    @Value("${bill.relativePath}")
    private   String relativePath;

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    public ProductControllerRest(final InventoryService inventoryService, final DenominationService denominationService) {
        this.inventoryService = inventoryService;
        this.denominationService = denominationService;

    }

    @GetMapping("/productRest/{id}")
    @Operation(summary = "CUSTOMER PROCESS--Get Inventory item by id")
    public Inventry getProductById(@PathVariable final int id) {
        return inventoryService.getOnlyInventryProductById(id);
    }


    @GetMapping("/getAllInventoryRest")
    @Operation(summary = "CUSTOMER PROCESS--Get LIST OF ALL Inventory item")
    @ResponseBody
    public List<InventoryDTO> getListOfAllInventoryRest() {
        return inventoryService.getListOfAllInventory();
    }


}
