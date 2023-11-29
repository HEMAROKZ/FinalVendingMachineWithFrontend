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

    private InventoryService inventoryService;
    private DenominationService denominationService;

    private static Logger log = LoggerFactory.getLogger(ProductController.class);

    public ProductController(InventoryService inventoryService,DenominationService denominationService) {
        this.inventoryService = inventoryService;
        this.denominationService = denominationService;
    }


    @GetMapping("/home")
    public ModelAndView showWelcomePage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("home");
        return model;
    }

    ////////////////////////////////-----------------------get by id
    @GetMapping("/product/{id}")
    @Operation(summary = "CUSTOMER PROCESS--Get Inventry item by id")
    public InventoryDTO getProductById(@PathVariable int id) {
        return inventoryService.getProductById(id);
    }

    /////////////////////


    @GetMapping("/Inventoryproduct/{id}")
    @Operation(summary = "CUSTOMER PROCESS--Get Inventry item by id")
    public Inventry getInventryProductById(@PathVariable int id) {
        return inventoryService.getInventryProductById(id);
    }


    //////////////////////////////////////////------------------get all inventry item


    @GetMapping("/getAllInventory")
    @Operation(summary = "CUSTOMER PROCESS--Get LIST OF ALL Inventry item")
    public ModelAndView getListOfAllInventory() {
        List<InventoryDTO> list = inventoryService.getListOfAllInventory();
        ModelAndView model = new ModelAndView();
        model.addObject("list", list);
        model.setViewName("getEmployee");
        return model;
    }

    /////////////////////////////////////---------------purchase product
//
//    /////////////////////   changes id did    ///////////////////////////////
    @RequestMapping(value = "purchaseproduct", method = RequestMethod.GET)
    public ModelAndView purchaseProduct() {

        ModelAndView model = new ModelAndView();
        List<InventoryDTO> list = inventoryService.getListOfAllInventory();
        model.addObject("list", list);

        model.setViewName("purchaseProduct");
        return model;

    }
    /////////////////////////////////////////////////////////////

    @RequestMapping(value = "purchaseproductpage", method = RequestMethod.GET)
    public ModelAndView purchaseProductpage() {

        ModelAndView model = new ModelAndView();
        model.setViewName("purchaseProductDetail");
        return model;

    }
///////////////////////////////////////////////////////////    for single item purchase don change anything
    @RequestMapping(value = "purchase-Inventryitem", method = RequestMethod.POST)
    public ModelAndView purchaseUserProduct(@RequestParam(value = "productId", required = true) int productId,
                                            @RequestParam(value = "price", required = true) int price,
                                            @RequestParam(value = "countOfProduct", required = true) int countOfProduct) {
        //CustomerInputDTO customerInputDTO = new CustomerInputDTO.CustomerInputBuilder().price(price).productId(productId).countOfProduct(countOfProduct).build();
        CustomerInputDTO customerInputDTO =CustomerInputDTO.builder()
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

    /////////////////////changes i did /////////////////////////////////

  // here   countsOfProduct is productInventoryCount and quantities is no of product user give to purchase

    @RequestMapping(value = "/purchasemultipleproductpage", method = RequestMethod.POST)
    public ModelAndView purchaseMultipleUserProducts(
            @RequestParam(value = "productIds", required = true) List<Integer> productIds,
            @RequestParam(value = "quantities", required = true) List<Integer> quantities,

            @RequestParam(value = "prices", required = true) List<Integer> prices,
            @RequestParam(value = "countsOfProduct", required = true) List<Integer> countsOfProduct,
            @RequestParam(value = "names", required = true) List<String> names) {

        List<PurchaseInputDTO> responseList = new ArrayList<>();
        int totalCost = 0;
        log.info("quantity for the purchase == {} ", quantities);

        log.info("quantity size for the purchase == {} ", quantities.size());


        // Iterate over the lists and process each product
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
                log.info("purchase product dto object"+ purchaseInputDTO);
                int individualCost = inventoryService.productCostCalculation(purchaseInputDTO);
                totalCost += individualCost;
                responseList.add(purchaseInputDTO);
            }
        }
        log.info("responseList product dto object"+ responseList);

        ModelAndView model = new ModelAndView();
        model.addObject("list", responseList);
        model.addObject("totalCost", totalCost);
        model.setViewName("multiplePurchasepage");

//here goes to multiple multiplePurchasepage.jsp file and from there .../multiplePurchaseOfProductFinal is called.
        return model;
    }


////////////////
    // here   countsOfProduct is productInventoryCount and quantities is no od product user give to purchase
@RequestMapping(value = "multiplePurchaseOfProductFinal", method = RequestMethod.POST)
public ModelAndView addMultipledDenominationForProduct(
        @RequestParam(name = "denomination50", defaultValue = "0") int denomination50,
        @RequestParam(name = "denomination20", defaultValue = "0") int denomination20,
        @RequestParam(name = "denomination10", defaultValue = "0") int denomination10,
        @RequestParam(name = "denomination5", defaultValue = "0") int denomination5,
        @RequestParam(name = "denomination2", defaultValue = "0") int denomination2,
        @RequestParam(name = "denomination1", defaultValue = "0") int denomination1,
        @RequestParam(name = "totalCost") int totalCost,
        @RequestParam(value = "productIds", required = true) List<Integer> productIds,
        @RequestParam(value = "names", required = true) List<String> names,
        @RequestParam(value = "prices", required = true) List<Integer> prices,
        @RequestParam(value = "countsOfProduct", required = true) List<Integer> countsOfProduct,
        @RequestParam(value = "quantities", required = true) List<Integer> quantities
) {

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

    // Iterate over the lists and process each product
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
        log.info("purchase product dto object" + purchaseInputDTO);

        responseList.add(purchaseInputDTO);

    }

    // Call the multiple purchase method
    PurchaseResult result = inventoryService.multiplePurchaseProduct(responseList, totalCost, denominationMap);
    // Call the service method to update denominations and check if denominations' total value covers the total cart price

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<11111111111111111111111111>>>>>>>>>>>>>>>>>>>>>>>>>>>
    denominationService.addUpdateDenominationCounts(denominationMap, totalCost);
    // Your existing logic for ModelAndView
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("multiplegreeting");
    modelAndView.addObject("result", result); // Passing the result to the view
    return modelAndView;
}

    private double calculateTotalPrice(List<Integer> prices, List<Integer> quantities) {
        double totalPrice = 0;
        for (int i = 0; i < prices.size(); i++) {
            totalPrice += prices.get(i) * quantities.get(i);
        }
        return totalPrice;
    }




}
