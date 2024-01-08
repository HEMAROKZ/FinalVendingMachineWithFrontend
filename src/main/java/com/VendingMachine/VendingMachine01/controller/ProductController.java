package com.VendingMachine.VendingMachine01.controller;

import com.VendingMachine.VendingMachine01.customeexception.CustomIOException;
import com.VendingMachine.VendingMachine01.dto.*;
import com.VendingMachine.VendingMachine01.dto.controllerDTO.DenominationType;
import com.VendingMachine.VendingMachine01.dto.controllerDTO.MultiplePurchaseDTO;
import com.VendingMachine.VendingMachine01.dto.controllerDTO.MultiplePurchaseInputDTO;
import com.VendingMachine.VendingMachine01.model.Inventry;
import com.VendingMachine.VendingMachine01.service.DenominationService;
import com.VendingMachine.VendingMachine01.service.InventoryService;
import com.VendingMachine.VendingMachine01.util.DenominationConfig;
import com.VendingMachine.VendingMachine01.util.FileUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "CUSTOMER PROCESS")
public class ProductController {

    private final InventoryService inventoryService;
    private final DenominationService denominationService;

    @Value("${bill.relativePath}")
    private   String relativePath;
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
    @Operation(summary = "CUSTOMER PROCESS--Get Inventory item by id")
    public InventoryDTO getProductById(@PathVariable final int id) {
        return inventoryService.getProductById(id);
    }

    @GetMapping("/Inventoryproduct/{id}")
    @Operation(summary = "CUSTOMER PROCESS--Get Inventory item by id")
    public Inventry getInventryProductById(@PathVariable final int id) {
        return inventoryService.getInventryProductById(id);
    }

    @GetMapping("/getAllInventory")
    @Operation(summary = "CUSTOMER PROCESS--Get LIST OF ALL Inventory item")
    public ModelAndView getListOfAllInventory() {
        List<InventoryDTO> list = inventoryService.getListOfAllInventory();
        ModelAndView model = new ModelAndView();
        model.addObject("list", list);
        model.setViewName("getInventoryList");
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

    @RequestMapping(value = "/purchasemultipleproductpage", method = RequestMethod.POST)
    public ModelAndView purchaseMultipleUserProducts(@ModelAttribute MultiplePurchaseInputDTO multiplePurchaseInputDTO) {
        // changed this added method in  inventoryService.processPurchaseRequest to avoid business login in controller
        log.info("quantity for the purchase == {} ", multiplePurchaseInputDTO.getQuantities());
        log.info("quantity size for the purchase == {} ", multiplePurchaseInputDTO.getQuantities().size());
        TotalCostResult totalCostResult= inventoryService.processPurchaseRequest(multiplePurchaseInputDTO.getProductIds(),multiplePurchaseInputDTO.getQuantities(), multiplePurchaseInputDTO.getPrices(), multiplePurchaseInputDTO.getCountsOfProduct(), multiplePurchaseInputDTO.getNames());
        List<PurchaseInputDTO> responseList =totalCostResult.getResponseList();
        int totalCost = totalCostResult.getTotalCost();
        ModelAndView model = new ModelAndView();
        model.addObject("list", responseList);
        model.addObject("totalCost", totalCost);  // Get the total cost after processing
        model.setViewName("multiplePurchasepage");  //from this jsp page  it comes to the below  method multiplePurchaseOfProductFinal when form submitted
        return model;
    }

@RequestMapping(value = "multiplePurchaseOfProductFinal", method = RequestMethod.POST)
public ModelAndView addMultipledDenominationForProduct(@ModelAttribute MultiplePurchaseDTO multiplePurchaseDTO, @ModelAttribute DenominationConfig denominationConfig) {
    Map<DenominationType, Integer> denominationMap = denominationConfig.getDenominationValues();
log.info("denominationMap======================================================"+denominationMap);

    List<PurchaseInputDTO> responseList = inventoryService.finalPurchaseRequest(multiplePurchaseDTO.getProductIds(), multiplePurchaseDTO.getQuantities(), multiplePurchaseDTO.getPrices(), multiplePurchaseDTO.getCountsOfProduct(), multiplePurchaseDTO.getNames());

    PurchaseResult result = inventoryService.multiplePurchaseProduct(responseList, multiplePurchaseDTO.getTotalCost(), denominationMap);
    denominationService.addUpdateDenominationCounts(denominationMap, multiplePurchaseDTO.getTotalCost());

    // method to Generate bill content
    String billContent = FileUtility.generateBillContent(result);
// code to Generate a unique file name based on date and time
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    String fileName = "Bill_" + now.format(formatter) + ".txt";
// Save the bill as a text file in the specified  directory
    FileUtility.generateBill(relativePath, fileName, billContent);
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("multiplegreeting");
    modelAndView.addObject("result", result);
    modelAndView.addObject("billFileName", fileName);
    return modelAndView;
}

@RequestMapping("/download")
public void downloadBill(@RequestParam("fileName") String fileName, HttpServletResponse response) {
    String filePath = relativePath+ fileName;
    log.info("file path --------------------->" + filePath);

    try (InputStream inputStream = new FileInputStream(filePath);
         OutputStream outputStream = response.getOutputStream()) {

        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

    } catch (IOException customIOException) {
        throw new CustomIOException("error occurred while downloading the bill");
    }
}



}
