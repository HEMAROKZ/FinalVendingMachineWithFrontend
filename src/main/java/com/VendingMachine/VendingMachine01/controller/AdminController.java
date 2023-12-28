package com.VendingMachine.VendingMachine01.controller;

import com.VendingMachine.VendingMachine01.dto.InventoryDTO;
import com.VendingMachine.VendingMachine01.model.Inventry;
import com.VendingMachine.VendingMachine01.service.AdminServices;
import com.VendingMachine.VendingMachine01.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@Tag(name = "ADMIN PROCESS")
public class AdminController {

    private final AdminServices adminServices;
    private final InventoryService inventoryService;

    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);


    public AdminController(final AdminServices adminServices, final InventoryService inventoryService) {
        this.adminServices = adminServices;
        this.inventoryService = inventoryService;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("login");
        log.info("Inside welcome controller");

        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView exit() {
        ModelAndView modelAndView = new ModelAndView("home");
        log.info("Inside welcome /logout in controller");
        return modelAndView;
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/delete/{id}")
    @Operation(summary = "ADMIN PROCESS--DELETE Inventory item")
    public ModelAndView deleteProductById(@PathVariable final int id) {
        adminServices.deleteProductById(id);
        List<InventoryDTO> list = inventoryService.getListOfAllInventory();

        ModelAndView model = new ModelAndView();
        model.addObject("list", list);
        model.setViewName("getInventoryList");
        return model;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/addinventoryitem")
    public ModelAndView addInventoryItemPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("addProduct");
        return model;
    }

@PreAuthorize("hasRole('USER')")
@RequestMapping(value = "add-Inventryitem", method = RequestMethod.POST)
public ModelAndView saveInventory(@RequestParam("productId") int productId,
                                  @RequestParam("name") String name,
                                  @RequestParam("productPrice") int productPrice,
                                  @RequestParam("productInventoryCount") int productInventoryCount){
        InventoryDTO inventoryDTO=InventoryDTO.builder()
                .withProductId(productId)
                .withName(name)
                .withProductPrice(productPrice)
                .withProductInventoryCount(productInventoryCount)
                .build();
    adminServices.saveInventory(inventoryDTO);
    List<InventoryDTO> list = inventoryService.getListOfAllInventory();

    ModelAndView model = new ModelAndView();
    model.addObject("list", list);
    model.setViewName("getInventoryList");
    return model;
}

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "update/user/{productId}", method = RequestMethod.GET)
    public ModelAndView updatePage(@PathVariable("productId") final int productId) {
        ModelAndView model = new ModelAndView("updateProduct");
        model.addObject("productId", productId);
        model.addObject("inventory", inventoryService.getOnlyInventryProductById(productId));
        return model;
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "update/user", method = RequestMethod.POST)
    public ModelAndView updateUser(@ModelAttribute Inventry inventory) {
        final int resp = adminServices.updateInventory(inventory);
        ModelAndView model = new ModelAndView();
        model.addObject("productId", inventory.getProductId());

        if (resp > 0) {
            model.addObject("msg", "User with id : " +  inventory.getProductId() + " updated successfully.");
            model.addObject("list", inventoryService.getListOfAllInventory());
            model.setViewName("getInventoryList");
            return model;
        } else {
            model.addObject("msg", "User with id : " +  inventory.getProductId() + " updation failed.");
            model.addObject("userDetail", inventoryService.getInventryProductById( inventory.getProductId()));
            model.setViewName("updateProduct");
            return model;
        }
    }
}
