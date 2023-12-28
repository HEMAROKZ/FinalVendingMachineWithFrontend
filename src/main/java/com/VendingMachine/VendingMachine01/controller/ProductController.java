package com.VendingMachine.VendingMachine01.controller;

import com.VendingMachine.VendingMachine01.dto.*;
import com.VendingMachine.VendingMachine01.dto.controllerDTO.MultiplePurchaseDTO;
import com.VendingMachine.VendingMachine01.dto.controllerDTO.MultiplePurchaseInputDTO;
import com.VendingMachine.VendingMachine01.model.Inventry;
import com.VendingMachine.VendingMachine01.service.DenominationService;
import com.VendingMachine.VendingMachine01.service.InventoryService;
import com.VendingMachine.VendingMachine01.util.FileUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    @RequestMapping(value = "purchase-Inventryitem", method = RequestMethod.POST)
    public ModelAndView purchaseUserProduct(@ModelAttribute CustomerInputDTO customerInputDTO) {

        VendingMachineOutputDTO respvariable = inventoryService.purchaseProduct(customerInputDTO);
        ModelAndView model = new ModelAndView();
        model.addObject("list", respvariable);
        model.setViewName("greeting");
        return model;
    }

    @RequestMapping(value = "/purchasemultipleproductpage", method = RequestMethod.POST)
    public ModelAndView purchaseMultipleUserProducts(@ModelAttribute MultiplePurchaseInputDTO multiplePurchaseInputDTO) {
        // changed this added method in  inventoryService.processPurchaseRequest to avoid bussiness login in controller

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
public ModelAndView addMultipledDenominationForProduct(@ModelAttribute MultiplePurchaseDTO multiplePurchaseDTO) {
    Map<Integer, Integer> denominationMap = new HashMap<>();
    denominationMap.put(50, multiplePurchaseDTO.getDenomination50());
    denominationMap.put(20, multiplePurchaseDTO.getDenomination20());
    denominationMap.put(10, multiplePurchaseDTO.getDenomination10());
    denominationMap.put(5, multiplePurchaseDTO.getDenomination5());
    denominationMap.put(2, multiplePurchaseDTO.getDenomination2());
    denominationMap.put(1, multiplePurchaseDTO.getDenomination1());

    List<PurchaseInputDTO> responseList = inventoryService.finalPurchaseRequest(multiplePurchaseDTO.getProductIds(), multiplePurchaseDTO.getQuantities(), multiplePurchaseDTO.getPrices(), multiplePurchaseDTO.getCountsOfProduct(), multiplePurchaseDTO.getNames());

    PurchaseResult result = inventoryService.multiplePurchaseProduct(responseList, multiplePurchaseDTO.getTotalCost(), denominationMap);
    denominationService.addUpdateDenominationCounts(denominationMap, multiplePurchaseDTO.getTotalCost());

    // Generate bill content
    String billContent = FileUtility.generateBillContent(result);
// Specify the relative directory path within the project
    String relativePath = "src/main/resources/bills/";

// Generate a unique file name based on date and time
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    String fileName = "Bill_" + now.format(formatter) + ".txt";

// Save the bill as a text file in the specified relative directory
    FileUtility.generateBill(relativePath, fileName, billContent);


    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("multiplegreeting");
    modelAndView.addObject("result", result);
    modelAndView.addObject("billFileName", fileName); // Add the file name to the model
    return modelAndView;
}
@RequestMapping("/download")
public void downloadBill(@RequestParam("fileName") String fileName, HttpServletResponse response) {
    String relativePath = "src/main/resources/bills/";
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

    } catch (IOException e) {
        e.printStackTrace(); // Handle the exception based on your application's needs
    }
}



}
