package com.VendingMachine.VendingMachine01.controller;

import com.VendingMachine.VendingMachine01.dto.InventoryDTO;
import com.VendingMachine.VendingMachine01.model.InitialBalanceAndPurchaseHistory;
import com.VendingMachine.VendingMachine01.service.AdminServices;
import com.VendingMachine.VendingMachine01.model.Inventry;
import com.VendingMachine.VendingMachine01.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@RestController
@Tag(name = "ADMIN PROCESS")
public class AdminController {

    private AdminServices adminServices;
    private InventoryService inventoryService;

    public AdminController( AdminServices adminServices,InventoryService inventoryService) {
        this.adminServices = adminServices;
        this.inventoryService=inventoryService;
    }


////////////////////////////////////////////////////////-------------delete item

    @GetMapping("/delete/{id}")
    @Operation(summary = "ADMIN PROCESS--DELETE  Inventory item ")
    public ModelAndView deleteProductById(@PathVariable int id) {
        adminServices.deleteProductById(id);
        List<InventoryDTO> list= inventoryService.getListOfAllInventory();

        ModelAndView model=new ModelAndView();
        model.addObject("list",list);

        model.setViewName("getEmployee");
        return model;
    }
////////////////////////////////////------------------add item
@GetMapping("/addinventoryitem")
public ModelAndView addInventoryItemPage() {
    ModelAndView model=new ModelAndView();
    model.setViewName("addEmployee");
    return model;
}


    @RequestMapping(value="add-Inventryitem",method = RequestMethod.POST)
    public ModelAndView saveEmployee(HttpServletRequest request) {
        InventoryDTO employee=new InventoryDTO(Integer.parseInt(request.getParameter("productId")), request.getParameter("name"), Integer.parseInt(request.getParameter("productPrice")), Integer.parseInt(request.getParameter("productInventoryCount")));
        adminServices.saveInventory(employee) ;
        List<InventoryDTO> list= inventoryService.getListOfAllInventory();

        ModelAndView model=new ModelAndView();
        model.addObject("list",list);

        model.setViewName("getEmployee");
        return model;
    }

////////////////////////////////////__________update


    @RequestMapping(value = "update/user/{productId}", method = RequestMethod.GET)
    public ModelAndView updatePage(@PathVariable("productId") int productId) {

        ModelAndView model=new ModelAndView("updateEmployee");

        model.addObject("productId",productId);
        model.addObject("inventory", inventoryService.getInventryProductById(productId));

//        model.setViewName("updateEmployee");
        return model;

    }
    @RequestMapping(value = "update/user", method = RequestMethod.POST)
    public ModelAndView updateUser(@RequestParam int productId, @RequestParam(value = "name", required = true) String name,
                             @RequestParam(value = "productPrice", required = true) int productPrice, @RequestParam("productInventoryCount") int productInventoryCount) {
        Inventry userDetail = new Inventry();
        userDetail.setProductId(productId);
        userDetail.setName(name);
        userDetail.setProductPrice(productPrice);
        userDetail.setProductInventoryCount(productInventoryCount);
        int resp = adminServices.updateInventory(userDetail);
        ModelAndView model=new ModelAndView();
        model.addObject("productId",productId);

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
