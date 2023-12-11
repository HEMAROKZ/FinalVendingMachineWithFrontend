package com.VendingMachine.VendingMachine01.controller;

import com.VendingMachine.VendingMachine01.dto.*;
import com.VendingMachine.VendingMachine01.model.Inventry;
import com.VendingMachine.VendingMachine01.service.DenominationService;
import com.VendingMachine.VendingMachine01.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@Tag(name = "CUSTOMER PROCESS")
public class ProductController {

    private final InventoryService inventoryService;
    private final DenominationService denominationService;

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    public ProductController(final InventoryService inventoryService, final DenominationService denominationService) {
        this.inventoryService = inventoryService;
        this.denominationService = denominationService;
    }

    @GetMapping("/home")
    public ModelAndView showWelcomePage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("home");
        return model;
    }

    @GetMapping("/product/{id}")
    @Operation(summary = "CUSTOMER PROCESS--Get Inventry item by id")
    public InventoryDTO getProductById(@PathVariable final int id) {
        return inventoryService.getProductById(id);
    }

    @GetMapping("/Inventoryproduct/{id}")
    @Operation(summary = "CUSTOMER PROCESS--Get Inventry item by id")
    public Inventry getInventryProductById(@PathVariable final int id) {
        return inventoryService.getInventryProductById(id);
    }

    @GetMapping("/getAllInventory")
    @Operation(summary = "CUSTOMER PROCESS--Get LIST OF ALL Inventry item")
    public ModelAndView getListOfAllInventory() {
        List<InventoryDTO> list = inventoryService.getListOfAllInventory();
        ModelAndView model = new ModelAndView();
        model.addObject("list", list);
        model.setViewName("getEmployee");
        return model;
    }

    @RequestMapping(value = "purchaseproduct", method = RequestMethod.GET)
    public ModelAndView purchaseProduct() {
        ModelAndView model = new ModelAndView();
        List<InventoryDTO> list = inventoryService.getListOfAllInventory();
        model.addObject("list", list);
        model.setViewName("purchaseProduct");
        return model;
    }

    @RequestMapping(value = "purchaseproductpage", method = RequestMethod.GET)
    public ModelAndView purchaseProductpage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("purchaseProductDetail");
        return model;
    }

    @RequestMapping(value = "purchase-Inventryitem", method = RequestMethod.POST)
    public ModelAndView purchaseUserProduct(
            @RequestParam(value = "productId", required = true) final int productId,
            @RequestParam(value = "price", required = true) final int price,
            @RequestParam(value = "countOfProduct", required = true) final int countOfProduct) {
        CustomerInputDTO customerInputDTO = CustomerInputDTO.builder()
                .withProductId(productId)
                .withPrice(price)
                .withCountOfProduct(countOfProduct)
                .build();
        VendingMachineOutputDTO respvariable = inventoryService.purchaseProduct(customerInputDTO);
        ModelAndView model = new ModelAndView();
        model.addObject("list", respvariable);
        model.setViewName("greeting");
        return model;
    }

    @RequestMapping(value = "/purchasemultipleproductpage", method = RequestMethod.POST)
    public ModelAndView purchaseMultipleUserProducts(
            @RequestParam(value = "productIds", required = true) final List<Integer> productIds,
            @RequestParam(value = "quantities", required = true) final List<Integer> quantities,
            @RequestParam(value = "prices", required = true) final List<Integer> prices,
            @RequestParam(value = "countsOfProduct", required = true) final List<Integer> countsOfProduct,
            @RequestParam(value = "names", required = true) final List<String> names) {

        List<PurchaseInputDTO> responseList = new ArrayList<>();
        int totalCost = 0;
        log.info("quantity for the purchase == {} ", quantities);
        log.info("quantity size for the purchase == {} ", quantities.size());

        for (int i = 0; i < quantities.size(); i++) {
            int quantity = quantities.get(i);
            int productId = productIds.get(i);
            int price = prices.get(i);
            int countOfProduct = countsOfProduct.get(i);
            String name = names.get(i);
            log.info("quantity for the purchase == {} ", quantity);
            if (quantity > 0) {
                PurchaseInputDTO purchaseInputDTO = PurchaseInputDTO.builder()
                        .withProductId(productId)
                        .withPrice(price)
                        .withCountOfProduct(countOfProduct)
                        .withQuantity(quantity)
                        .withName(name)
                        .build();
                int individualCost = inventoryService.productCostCalculation(purchaseInputDTO);
                totalCost += individualCost;
                responseList.add(purchaseInputDTO);
            }
        }

        ModelAndView model = new ModelAndView();
        model.addObject("list", responseList);
        model.addObject("totalCost", totalCost);
        model.setViewName("multiplePurchasepage");
        return model;
    }

    @RequestMapping(value = "multiplePurchaseOfProductFinal", method = RequestMethod.POST)
    public ModelAndView addMultipledDenominationForProduct(
            @RequestParam(name = "denomination50", defaultValue = "0") final int denomination50,
            @RequestParam(name = "denomination20", defaultValue = "0") final int denomination20,
            @RequestParam(name = "denomination10", defaultValue = "0") final int denomination10,
            @RequestParam(name = "denomination5", defaultValue = "0") final int denomination5,
            @RequestParam(name = "denomination2", defaultValue = "0") final int denomination2,
            @RequestParam(name = "denomination1", defaultValue = "0") final int denomination1,
            @RequestParam(name = "totalCost") final int totalCost,
            @RequestParam(value = "productIds", required = true) final List<Integer> productIds,
            @RequestParam(value = "names", required = true) final List<String> names,
            @RequestParam(value = "prices", required = true) final List<Integer> prices,
            @RequestParam(value = "countsOfProduct", required = true) final List<Integer> countsOfProduct,
            @RequestParam(value = "quantities", required = true) final List<Integer> quantities) {

        Map<Integer, Integer> denominationMap = new HashMap<>();
        denominationMap.put(50, denomination50);
        denominationMap.put(20, denomination20);
        denominationMap.put(10, denomination10);
        denominationMap.put(5, denomination5);
        denominationMap.put(2, denomination2);
        denominationMap.put(1, denomination1);

        List<PurchaseInputDTO> responseList = new ArrayList<>();
        log.info("quantity for the purchase == {} ", quantities);
        log.info("quantity size for the purchase == {} ", quantities.size());

        for (int i = 0; i < productIds.size(); i++) {
            int quantity = quantities.get(i);
            int productId = productIds.get(i);
            int price = prices.get(i);
            int countOfProduct = countsOfProduct.get(i);
            String name = names.get(i);
            log.info("quantity for the purchase == {} ", quantity);

            PurchaseInputDTO purchaseInputDTO = PurchaseInputDTO.builder()
                    .withProductId(productId)
                    .withPrice(price)
                    .withCountOfProduct(countOfProduct)
                    .withQuantity(quantity)
                    .withName(name)
                    .build();
            responseList.add(purchaseInputDTO);
        }

        PurchaseResult result = inventoryService.multiplePurchaseProduct(responseList, totalCost, denominationMap);
        denominationService.addUpdateDenominationCounts(denominationMap, totalCost);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("multiplegreeting");
        modelAndView.addObject("result", result);
        return modelAndView;
    }

    private double calculateTotalPrice(final List<Integer> prices, final List<Integer> quantities) {
        double totalPrice = 0;
        for (int i = 0; i < prices.size(); i++) {
            totalPrice += prices.get(i) * quantities.get(i);
        }
        return totalPrice;
    }
}
