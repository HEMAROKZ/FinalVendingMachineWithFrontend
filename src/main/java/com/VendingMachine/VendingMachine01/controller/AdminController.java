package com.VendingMachine.VendingMachine01.controller;

import com.VendingMachine.VendingMachine01.dto.InventoryDTO;
import com.VendingMachine.VendingMachine01.model.Inventry;
import com.VendingMachine.VendingMachine01.service.AdminServices;
import com.VendingMachine.VendingMachine01.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@Tag(name = "ADMIN PROCESS")
public class AdminController {

    private final AdminServices adminServices;
    private final InventoryService inventoryService;

    public AdminController(final AdminServices adminServices, final InventoryService inventoryService) {
        this.adminServices = adminServices;
        this.inventoryService = inventoryService;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("login");
        System.out.println("Inside welcome controller");
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView exit() {
        ModelAndView modelAndView = new ModelAndView("home");
        System.out.println("Inside welcome controller");
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
        model.setViewName("getEmployee");
        return model;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/addinventoryitem")
    public ModelAndView addInventoryItemPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("addEmployee");
        return model;
    }

     @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "add-Inventryitem", method = RequestMethod.POST)
    public ModelAndView saveEmployee(final HttpServletRequest request) {
        final InventoryDTO employee = new InventoryDTO(
                Integer.parseInt(request.getParameter("productId")),
                request.getParameter("name"),
                Integer.parseInt(request.getParameter("productPrice")),
                Integer.parseInt(request.getParameter("productInventoryCount"))
        );
        adminServices.saveInventory(employee);
        List<InventoryDTO> list = inventoryService.getListOfAllInventory();

        ModelAndView model = new ModelAndView();
        model.addObject("list", list);
        model.setViewName("getEmployee");
        return model;
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "update/user/{productId}", method = RequestMethod.GET)
    public ModelAndView updatePage(@PathVariable("productId") final int productId) {
        ModelAndView model = new ModelAndView("updateEmployee");
        model.addObject("productId", productId);
        model.addObject("inventory", inventoryService.getOnlyInventryProductById(productId));
        return model;
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "update/user", method = RequestMethod.POST)
    public ModelAndView updateUser(
            @RequestParam final int productId,
            @RequestParam(value = "name", required = true) final String name,
            @RequestParam(value = "productPrice", required = true) final int productPrice,
            @RequestParam("productInventoryCount") final int productInventoryCount) {
        final Inventry userDetail = new Inventry();
        userDetail.setProductId(productId);
        userDetail.setName(name);
        userDetail.setProductPrice(productPrice);
        userDetail.setProductInventoryCount(productInventoryCount);
        final int resp = adminServices.updateInventory(userDetail);
        ModelAndView model = new ModelAndView();
        model.addObject("productId", productId);

        if (resp > 0) {
            model.addObject("msg", "User with id : " + productId + " updated successfully.");
            model.addObject("list", inventoryService.getListOfAllInventory());
            model.setViewName("getEmployee");
            return model;
        } else {
            model.addObject("msg", "User with id : " + productId + " updation failed.");
            model.addObject("userDetail", inventoryService.getInventryProductById(productId));
            model.setViewName("updateEmployee");
            return model;
        }
    }
}
