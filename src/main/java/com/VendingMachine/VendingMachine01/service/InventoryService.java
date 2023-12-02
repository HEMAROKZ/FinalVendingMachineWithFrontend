package com.VendingMachine.VendingMachine01.service;

import com.VendingMachine.VendingMachine01.customeexception.*;
import com.VendingMachine.VendingMachine01.dao.*;
import com.VendingMachine.VendingMachine01.dto.*;
import com.VendingMachine.VendingMachine01.model.InitialBalanceAndPurchaseHistory;

import com.VendingMachine.VendingMachine01.model.Inventry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final InventoryDAO repository;
    private final InitialBalanceDAO initialBalanceDAOImp;
    private final DenominationService denominationService;


    private static Logger log = LoggerFactory.getLogger(InventoryService.class);

    public InventoryService(InventoryDAO repository, InitialBalanceDAO initialBalanceDAOImp, DenominationService denominationService) {
        this.repository = repository;
        this.initialBalanceDAOImp = initialBalanceDAOImp;
        this.denominationService = denominationService;
    }


    ///////////////////////////////////// here used constructor injection insted of autowired injection


    public List<InventoryDTO> getListOfAllInventory() {
        return allProductToUserInventory(repository.findAll());
    }
//////////////////////////////////////////////////////


    ///////////////////////////////////////////////////
    public List<InventoryDTO> allProductToUserInventory(List<Inventry> allInvProduct) {
        return allInvProduct.stream()
                .map(this::productToUserProduct)
                .collect(Collectors.toList());
    }

    public InventoryDTO getProductById(int productId) {
        log.info("product id in get product by id == {} ", productId);
        List<Inventry> product = repository.findById(productId);
        Inventry selectedProduct = product.stream()
                .findFirst()
                .orElseThrow(() -> new ProductIdNotFoundException("Invalid product id given in URL....!!!!"));

        if (selectedProduct.getProductInventoryCount() < 1) {
            throw new ProductUnavialableException(selectedProduct.getName() + " is Out of Stock..!!");
        }

        return productToUserProduct(selectedProduct);
    }
///////////////

    public Inventry getInventryProductById(int productId) {
        log.info("product id in get product by id == {} ", productId);
        if (repository.findById(productId).isEmpty()) {  ///check this  for null pointer exception
            throw new ProductIdNotFoundException("invalid product id given in url ....!!!!");
        } else if (repository.findById(productId).get(0).getProductInventoryCount() < 1) {
            throw new ProductUnavialableException(repository.findById(productId).get(0).getName() + " is Out of Stock..!!");
        }
        return repository.findById(productId).get(0);
    }

    ////////////////////////
    public InventoryDTO productToUserProduct(Inventry inventory) {
        return new InventoryDTO(inventory.getProductId(), inventory.getName(), inventory.getProductPrice(), inventory.getProductInventoryCount());
    }


    /////////////////////////////////////////////////////////

    public VendingMachineOutputDTO purchaseProduct( final CustomerInputDTO customerInputDTO) {
        var productId = customerInputDTO.getProductId();
        var inputPrice = customerInputDTO.getPrice();
        var countOfProduct = customerInputDTO.getCountOfProduct();

        log.info("product id in purchase product == {} ", productId);

        var productList = repository.findById(productId);
        if (productList.isEmpty()) {
            throw new ProductIdNotFoundException("Invalid product id given in URL....!!!!");
        }

        var inventory = productList.get(0);

        if (countOfProduct <= inventory.getProductInventoryCount()) {
            if (inputPrice < (inventory.getProductPrice() * countOfProduct)) {
                throw new InsufficientInputCashException(inputPrice + " rupees is not enough for " + inventory.getName());
            }

            var change = inputPrice - (inventory.getProductPrice() * countOfProduct);

            if (initialBalanceDAOImp.getChange().getInitialBalance() < change) {
                throw new InsufficientInitialBalanceException("Sorry No Change!!");
            }

            log.info("change amount for the purchase == {} ", change);

            var newInitialBalance = initialBalanceDAOImp.getChange().getInitialBalance() - change;

            log.info("new balance in the vending machine == {}", newInitialBalance);

            // Calculate denominations for the change
            Map<Integer, Integer> customDenominations = denominationService.getCustomDenominationsFromDatabase();
            Map<Integer, Integer> denominationMap = denominationService.calculateCustomChangeDenominations(change, customDenominations);

            System.out.println("Change Denominations:");
            if (denominationService.isExactChangeAvailable(change, denominationMap)) {
                for (Map.Entry<Integer, Integer> entry : denominationMap.entrySet()) {
                    int denomination = entry.getKey();
                    int count = entry.getValue();

                    if (count > 0) {
                        System.out.println(denomination + " Rupees: " + count + " notes/coins");
                    }
                }
            } else {
                throw new NoExactChangeException("No exact change available. Please provide the exact amount.");
            }

            // Update denomination counts in the database
            denominationService.updateDenominationCounts(denominationMap);

            // Create a new object to populate the initial balance table and the record table
            InitialBalanceAndPurchaseHistory currentTransaction = new InitialBalanceAndPurchaseHistory(0, inventory.getProductId(), inventory.getName(), inventory.getProductPrice(), inputPrice, change, newInitialBalance);

            // Calling update stock method from the InventoryDAOImplementation class
            repository.updatedStock(inventory.getProductId(), inventory.getProductInventoryCount() - countOfProduct);
            initialBalanceDAOImp.saveTransaction(currentTransaction);

            // Return VendingMachineOutputDTO along with denomination map
            return new VendingMachineOutputDTO(inventory.getName(), inventory.getProductPrice(), change, denominationMap);
        } else {
            throw new ProductUnavialableException(inventory.getName() + " is in limited stock. Only " + inventory.getProductInventoryCount() + " left!");
        }
    }

//////////////////////////////////////////////////////////


//will retun individualCost for the product that we opt for purchase

    public int productCostCalculation(final PurchaseInputDTO purchaseInputDTOList) {

        // Iterate over the list of PurchaseInputDTO and calculate individual costs
        int individualCost = purchaseInputDTOList.getPrice() * purchaseInputDTOList.getQuantity();

        return individualCost;
    }
    ////////////////////////////////////////working on this remember..................
    public PurchaseResult multiplePurchaseProduct(final List<PurchaseInputDTO> purchaseInputDTO, int totalCost, Map<Integer, Integer> inputDenominationMap) {

        log.info("product id in purchase product == {} ", totalCost);

        log.info("inSIDE  multiplePurchaseProduct METHOD");
        var change=0;

        var inputAmount = denominationService.totalDenominationAmount(inputDenominationMap, totalCost);

        if(inputAmount>=totalCost ){
             change = inputAmount - totalCost;
        }

    else
        {
            throw new InsufficientInputCashException("total denomination value is less than the total cost of the product you r trying to purchase");
        }
        log.info("inSIDE  multiplePurchaseProduct METHOD CHANGE VALUE IS ====  {}",change);

        if (initialBalanceDAOImp.getChange().getInitialBalance() < change) {
            throw new InsufficientInitialBalanceException("Sorry No Change!!");
        }

        log.info("change amount for the purchase == {} ", change);

        var newInitialBalance = initialBalanceDAOImp.getChange().getInitialBalance() - change;

        log.info("new balance in the vending machine == {}", newInitialBalance);

        // Calculate denominations for the change
        Map<Integer, Integer> customDenominations = denominationService.getCustomDenominationsFromDatabase();
        Map<Integer, Integer> denominationMap = denominationService.calculateCustomChangeDenominations(change, customDenominations);
        log.info("Change Denominations:");

        if (denominationService.isExactChangeAvailable(change, denominationMap)) {
            for (Map.Entry<Integer, Integer> entry : denominationMap.entrySet()) {
                int denomination = entry.getKey();
                int count = entry.getValue();

                if (count > 0) {
                    log.info(denomination + " Rupees: " + count + " notes/coins");
                }
            }
        } else {
            throw new NoExactChangeException("No exact change available. Please provide the exact amount.");
        }


        List<Map<String, Object>> resultList = new ArrayList<>();

        for (PurchaseInputDTO purchaseInputDTOList : purchaseInputDTO) {
            var productId = purchaseInputDTOList.getProductId();
            var price = purchaseInputDTOList.getPrice();
            var countOfProduct = purchaseInputDTOList.getCountOfProduct(); //inventory product count
            var quantity = purchaseInputDTOList.getQuantity();
            var name = purchaseInputDTOList.getName();

            InitialBalanceAndPurchaseHistory currentTransaction = new InitialBalanceAndPurchaseHistory(0, productId, name, price, inputAmount, change, newInitialBalance);

            // Calling update stock method from the InventoryDAOImplementation class
            repository.updatedStock(productId, countOfProduct - quantity);
            initialBalanceDAOImp.saveTransaction(currentTransaction);

            // Add the result for each item to the list
            Map<String, Object> result = new HashMap<>();
            result.put("name", name);
            result.put("price", price);
            result.put("quantity",quantity);
            resultList.add(result);
        }
        // Update denomination counts in the database
        denominationService.updateDenominationCounts(denominationMap);

        initialBalanceDAOImp.initialBalanceUpdate( inputAmount);
        // Return a single result containing the change, denomination, and items
        return new PurchaseResult(change, denominationMap, resultList);

    }



    //method to calculate the total price of purchased products from the list
    private double calculateTotalPrice(final List<PurchaseInputDTO> purchaseInputDTO) {
        double totalPrice = 0;
        for (PurchaseInputDTO inputDTO : purchaseInputDTO) {
            totalPrice += inputDTO.getPrice() * inputDTO.getCountOfProduct();
        }
        return totalPrice;
    }
}
