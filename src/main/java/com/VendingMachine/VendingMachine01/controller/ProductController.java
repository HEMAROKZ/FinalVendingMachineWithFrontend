package com.VendingMachine.VendingMachine01.controller;

import com.VendingMachine.VendingMachine01.customeexception.CustomIOException;
import com.VendingMachine.VendingMachine01.customeexception.NoExactChangeException;
import com.VendingMachine.VendingMachine01.customeexception.ProductUnavialableException;
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
import javax.servlet.http.HttpSession;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@Tag(name = "CUSTOMER PROCESS")
public class ProductController {

    private final InventoryService inventoryService;
    private final DenominationService denominationService;

    @Value("${bill.relativePath}")
    private   String relativePath;

    private static final String BILLING_COUNTER_SESSION_KEY = "billingCounter";
    private static final int MAX_BILLING_COUNTERS = 3;

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    public ProductController(final InventoryService inventoryService, final DenominationService denominationService) {
        this.inventoryService = inventoryService;
        this.denominationService = denominationService;

    }

    @GetMapping("/home")
    public ModelAndView showWelcomePage() {//HttpSession session
        ModelAndView model = new ModelAndView();
    //    session.removeAttribute(BILLING_COUNTER_SESSION_KEY);

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

//    @RequestMapping(value = "purchaseproduct", method = RequestMethod.GET)
//    public ModelAndView purchaseProduct(HttpSession session) {//
//        ModelAndView model = new ModelAndView();
//
//        // Check if billing counter is already assigned to the session
//        if (session.getAttribute(BILLING_COUNTER_SESSION_KEY) == null) {
//            // Assign a new billing counter if not assigned
//            int billingCounter = assignBillingCounter(session);
//            model.addObject("billingCounter", billingCounter);
//        } else {
//            // Use the existing billing counter
//            int billingCounter = (int) session.getAttribute(BILLING_COUNTER_SESSION_KEY);
//            model.addObject("billingCounter", billingCounter);
//        }
//        List<InventoryDTO> list = inventoryService.getListOfAllInventory();
//        model.addObject("list", list);
//        model.setViewName("purchaseProduct");
//        return model;
//    }

    @RequestMapping(value = "purchaseproduct", method = RequestMethod.GET)
    public ModelAndView purchaseProduct(HttpSession session) {
        ModelAndView model = new ModelAndView();

        // Log the current session ID and billing counter for debugging
        System.out.println("Session ID: " + session.getId());
        System.out.println("Existing Billing Counter: " + session.getAttribute(BILLING_COUNTER_SESSION_KEY));

        // Check if billing counter is already assigned to the session
        if (session.getAttribute(BILLING_COUNTER_SESSION_KEY) == null) {
            // Log that a new billing counter is being assigned
            System.out.println("Assigning a new billing counter");

            // Assign a new billing counter if not assigned
            int billingCounter = assignBillingCounter(session);
            model.addObject("billingCounter", billingCounter);
        } else {
            // Log that the existing billing counter is being used
            System.out.println("Using existing billing counter");

            // Use the existing billing counter
            int billingCounter = (int) session.getAttribute(BILLING_COUNTER_SESSION_KEY);
            model.addObject("billingCounter", billingCounter);
        }

        List<InventoryDTO> list = inventoryService.getListOfAllInventory();
        model.addObject("list", list);

        model.setViewName("purchaseProduct");
        return model;
    }


    @RequestMapping(value = "/purchasemultipleproductpage", method = RequestMethod.POST)
    public ModelAndView purchaseMultipleUserProducts(@ModelAttribute MultiplePurchaseInputDTO multiplePurchaseInputDTO,@RequestParam int billingCounter ) {//
        // changed this added method in  inventoryService.processPurchaseRequest to avoid business login in controller
        log.info("billingCounter for the purchase == {} ", billingCounter);

        log.info("quantity for the purchase == {} ", multiplePurchaseInputDTO.getQuantities());
        log.info("quantity size for the purchase == {} ", multiplePurchaseInputDTO.getQuantities().size());
        TotalCostResult totalCostResult= inventoryService.processPurchaseRequest(multiplePurchaseInputDTO.getProductIds(),multiplePurchaseInputDTO.getQuantities(), multiplePurchaseInputDTO.getPrices(), multiplePurchaseInputDTO.getCountsOfProduct(), multiplePurchaseInputDTO.getNames());
        List<PurchaseInputDTO> responseList =totalCostResult.getResponseList();
        int totalCost = totalCostResult.getTotalCost();
        ModelAndView model = new ModelAndView();

        model.addObject("billingCounter",billingCounter);

        model.addObject("list", responseList);
        model.addObject("totalCost", totalCost);  // Get the total cost after processing
        model.setViewName("multiplePurchasepage");  //from this jsp page  it comes to the below  method multiplePurchaseOfProductFinal when form submitted
        return model;
    }

    @RequestMapping(value = "multiplePurchaseOfProductFinal", method = RequestMethod.POST)
    public ModelAndView addMultipledDenominationForProduct(@ModelAttribute MultiplePurchaseDTO multiplePurchaseDTO, @ModelAttribute DenominationConfig denominationConfig , HttpSession session,@RequestParam int billingCounter) {//,@RequestParam int billingCounter
        Map<DenominationType, Integer> denominationMap = denominationConfig.getDenominationValues();
        log.info("denominationMap======================================================"+denominationMap);

        List<PurchaseInputDTO> responseList = inventoryService.finalPurchaseRequest(multiplePurchaseDTO.getProductIds(), multiplePurchaseDTO.getQuantities(), multiplePurchaseDTO.getPrices(), multiplePurchaseDTO.getCountsOfProduct(), multiplePurchaseDTO.getNames());

        PurchaseResult result = inventoryService.multiplePurchaseProduct(responseList, multiplePurchaseDTO.getTotalCost(), denominationMap,billingCounter);
        denominationService.addUpdateDenominationCounts(denominationMap, multiplePurchaseDTO.getTotalCost());

        // method to Generate bill content
        String billContent = FileUtility.generateBillContent(result);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String fileName = "Bill_" + now.format(formatter) + ".txt";
        // Save the bill as a text file in the specified  directory
        FileUtility.generateBill(relativePath, fileName, billContent);

        releaseBillingCounter(session);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("multiplegreeting");
        modelAndView.addObject("result", result);
        modelAndView.addObject("billFileName", fileName);
        return modelAndView;
    }
    @GetMapping("/releaseBillingCounter")
    @ResponseBody
    public String releaseBillingCounters(HttpSession session) {
        // Release the billing counter
        releaseBillingCounter(session);
        return "Billing counter released";
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

    /////////////////////////////////////////////////
//    private synchronized int assignBillingCounter(HttpSession session) {
//        // Get the current active billing counter
//        Integer activeCounters = (Integer) session.getServletContext().getAttribute("activeBillingCounters");
//        if (activeCounters == null) {
//            activeCounters = 0;
//        }
//        // Check if the maximum number of billing counters is reached
//        if (activeCounters >= MAX_BILLING_COUNTERS) {
//            throw new ProductUnavialableException("All billing counters are currently in use");
//        }
//
//        // Increment the active counters and assign the next billing counter
//        activeCounters++;
//        int billingCounter = activeCounters;
//
//        // Update session and application attributes
//        session.setAttribute(BILLING_COUNTER_SESSION_KEY, billingCounter);
//        session.getServletContext().setAttribute("activeBillingCounters", activeCounters);
//
//        return billingCounter;
//    }
    private synchronized int assignBillingCounter(HttpSession session) {
        Set<Integer> occupiedCounters = (Set<Integer>) session.getServletContext().getAttribute("occupiedBillingCounters");
        if (occupiedCounters == null) {
            occupiedCounters = new HashSet<>();
        }

        // Find the first available billing counter
        int billingCounter = 1;
        while (occupiedCounters.contains(billingCounter)) {
            billingCounter++;
            if (billingCounter > MAX_BILLING_COUNTERS) {
                // Handle error when all counters are in use
                // You can throw a custom exception or redirect the user to an error page
                throw new ProductUnavialableException("All billing counters are currently in use");
            }
        }

        // Mark the counter as occupied
        occupiedCounters.add(billingCounter);

        // Update session and application attributes
        session.setAttribute(BILLING_COUNTER_SESSION_KEY, billingCounter);
        session.getServletContext().setAttribute("occupiedBillingCounters", occupiedCounters);

        return billingCounter;
    }
//    private synchronized void releaseBillingCounter(HttpSession session) {
//        // Decrement the active counters when billing is finished
//        Integer activeCounters = (Integer) session.getServletContext().getAttribute("activeBillingCounters");
//        if (activeCounters != null && activeCounters > 0) {
//            activeCounters--;
//            session.getServletContext().setAttribute("activeBillingCounters", activeCounters);
//        }
//    }

    private synchronized void releaseBillingCounter(HttpSession session) {
        // Get the current billing counter
        Integer billingCounter = (Integer) session.getAttribute(BILLING_COUNTER_SESSION_KEY);

        if (billingCounter != null) {
            // Mark the billing counter as available
            Set<Integer> occupiedCounters = (Set<Integer>) session.getServletContext().getAttribute("occupiedBillingCounters");
            if (occupiedCounters != null) {
                occupiedCounters.remove(billingCounter);
                session.getServletContext().setAttribute("occupiedBillingCounters", occupiedCounters);
            }

            // Clear the billing counter from the session
            session.removeAttribute(BILLING_COUNTER_SESSION_KEY);
        }
    }
    ///////////////////////////////////////////////////////////////


}
