package com.VendingMachine.controller;

import com.VendingMachine.dto.InventoryDTO;
import com.VendingMachine.model.Inventry;
import com.VendingMachine.service.AdminServices;
import com.VendingMachine.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
//import com.VendingMachine.security.JwtTokenUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@CrossOrigin()
@Tag(name = "ADMIN PROCESS")
public class    AdminController {

    private final AdminServices adminServices;
    private final InventoryService inventoryService;

  //  private final JwtTokenUtil jwtTokenUtil;

    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);


    public AdminController(final AdminServices adminServices, final InventoryService inventoryService ) {//JwtTokenUtil jwtTokenUtil
        this.adminServices = adminServices;
        this.inventoryService = inventoryService;
       // this.jwtTokenUtil = jwtTokenUtil;
    }

//
//    @PostMapping("/perform_login")
//    public ModelAndView performLogin(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
//        // Authenticate the user
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//
//        // Generate JWT token
//        String jwtToken = jwtTokenUtil.generateToken(authentication);
//
//        // Set the token in a cookie
//        Cookie cookie = new Cookie("JWT-TOKEN", jwtToken);
//        cookie.setHttpOnly(true);
//        response.addCookie(cookie);
//
//        // Redirect to a secured page
//        return new ModelAndView("redirect:/secured-page");
//    }
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
    //@PreAuthorize("hasRole('USER')")
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


   // @PreAuthorize("hasRole('USER')")
    @GetMapping("/addinventoryitem")
    public ModelAndView addInventoryItemPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("addProduct");
        return model;
    }

//@PreAuthorize("hasRole('USER')")
@RequestMapping(value = "add-Inventryitem", method = RequestMethod.POST)
public ModelAndView saveInventory(@ModelAttribute Inventry inventory){
    InventoryDTO inventoryDTO=InventoryDTO.builder()
            .withProductId(inventory.getProductId())
            .withName(inventory.getName())
            .withProductPrice(inventory.getProductPrice())
            .withProductInventoryCount(inventory.getProductInventoryCount())
            .build();
    adminServices.saveInventory(inventoryDTO);
    List<InventoryDTO> list = inventoryService.getListOfAllInventory();

    ModelAndView model = new ModelAndView();
    model.addObject("list", list);
    model.setViewName("getInventoryList");
    return model;
}

  //  @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "update/user/{productId}", method = RequestMethod.GET)
    public ModelAndView updatePage(@PathVariable("productId") final int productId) {
        ModelAndView model = new ModelAndView("updateProduct");
        model.addObject("productId", productId);
        model.addObject("inventory", inventoryService.getOnlyInventryProductById(productId));
        return model;
    }

  //  @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "update/user", method = RequestMethod.POST)
    public ModelAndView updateUser(@ModelAttribute Inventry inventory) {
        final int resp = adminServices.updateInventory(inventory);
        ModelAndView model = new ModelAndView();
        model.addObject("productId", inventory.getProductId());

        if (resp > 0) {
            model.addObject("msg", "product with id : " +  inventory.getProductId() + " updated successfully.");
            model.addObject("list", inventoryService.getListOfAllInventory());
            model.setViewName("getInventoryList");
            return model;
        } else {
            model.addObject("msg", "product with id : " +  inventory.getProductId() + " updation failed.");
            model.addObject("userDetail", inventoryService.getInventryProductById( inventory.getProductId()));
            model.setViewName("updateProduct");
            return model;
        }
    }
}
