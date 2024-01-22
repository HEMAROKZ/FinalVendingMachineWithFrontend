package com.VendingMachine.VendingMachine01.service;

import com.VendingMachine.VendingMachine01.customeexception.*;
import com.VendingMachine.VendingMachine01.dao.*;
import com.VendingMachine.VendingMachine01.dto.*;
import com.VendingMachine.VendingMachine01.dto.controllerDTO.DenominationType;
import com.VendingMachine.VendingMachine01.model.InitialBalanceAndPurchaseHistory;

import com.VendingMachine.VendingMachine01.model.Inventry;
import com.VendingMachine.VendingMachine01.model.OrderLine;
import com.VendingMachine.VendingMachine01.util.TransactionIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class InventoryService {

    private final InventoryDAO repository;
    private final InitialBalanceDAO initialBalanceDAOImp;
    private final DenominationService denominationService;


    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);

    public InventoryService(InventoryDAO repository, InitialBalanceDAO initialBalanceDAOImp, DenominationService denominationService) {
        this.repository = repository;
        this.initialBalanceDAOImp = initialBalanceDAOImp;
        this.denominationService = denominationService;
    }


    ///////////////////////////////////// here used constructor injection instead of autowired injection


    public List<InventoryDTO> getListOfAllInventory() {
        return allProductToUserInventory(repository.findAll());
    }


    ///////////////////////////////////////////////////
    public List<InventoryDTO> allProductToUserInventory(List<Inventry> allInvProduct) {
        return allInvProduct.stream()
                .map(this::productToUserProduct)
                .collect(Collectors.toList());
    }

    public InventoryDTO getProductById(int productId) {
        List<Inventry> product = repository.findById(productId);
        Inventry selectedProduct = product.stream()
                .findFirst()
                .orElseThrow(() -> new ProductIdNotFoundException("Invalid product id given in URL....!!!!"));

        if (selectedProduct.getProductInventoryCount() < 1) {
            throw new ProductUnavialableException(selectedProduct.getName() + " is Out of Stock..!!");
        }

        return productToUserProduct(selectedProduct);
    }

    public Inventry getInventryProductById(int productId) {
        if (repository.findById(productId).isEmpty()) {  ///check this  for null pointer exception
            throw new ProductIdNotFoundException("invalid product id given in url ....!!!!");
        } else if (repository.findById(productId).get(0).getProductInventoryCount() < 1) {
            throw new ProductUnavialableException(repository.findById(productId).get(0).getName() + " is Out of Stock..!!");
        }
        return repository.findById(productId).get(0);
    }

    public Inventry getOnlyInventryProductById(int productId) {

        return repository.findById(productId).get(0);
    }

    ////////////////////////
    public InventoryDTO productToUserProduct(Inventry inventory) {
        return new InventoryDTO(inventory.getProductId(), inventory.getName(), inventory.getProductPrice(), inventory.getProductInventoryCount());
    }

//will return individualCost for the product that we opt for purchase
    public PurchaseResult multiplePurchaseProduct(final List<PurchaseInputDTO> purchaseInputDTO, int totalCost, Map<DenominationType, Integer> inputDenominationMap,int billingCounter) {

        var change=0;

        var inputAmount = denominationService.totalDenominationAmount(inputDenominationMap, totalCost);

        if(inputAmount>=totalCost ){
            change = inputAmount - totalCost;
        }

        else
        {
            throw new InsufficientInputCashException("total denomination value is less than the total cost of the product you r trying to purchase");
        }

        if (initialBalanceDAOImp.getChange().getVendingMachineBalance() < change) {
            throw new InsufficientInitialBalanceException("Sorry No Change!!");
        }

        var newInitialBalance = initialBalanceDAOImp.getChange().getVendingMachineBalance() - change;

        Map<Integer, Integer> customDenominations = denominationService.getCustomDenominationsFromDatabase();

        Map<Integer, Integer> denominationMap = denominationService.calculateCustomChangeDenominations(change, customDenominations);

        if (!denominationService.isExactChangeAvailable(change, denominationMap)) {
            throw new NoExactChangeException("No exact change available. Please provide the exact amount.");
        }

        denominationMap.forEach((denomination, count) -> {
            if (count > 0) {
                log.info(denomination + " Rupees: " + count + " notes/coins");
            }
        });

        List<Map<String, Object>> resultList = new ArrayList<>();
        int transactionId = TransactionIdGenerator.generateTransactionId();
        StringBuilder productIdsBuilder = new StringBuilder();

        for (PurchaseInputDTO purchaseInputDTOList : purchaseInputDTO) {
            var productId = purchaseInputDTOList.getProductId();
            var price = purchaseInputDTOList.getPrice();
            var countOfProduct = purchaseInputDTOList.getCountOfProduct(); // inventory product count
            var quantity = purchaseInputDTOList.getQuantity();
            var name = purchaseInputDTOList.getName();

            if (productIdsBuilder.length() > 0) {
                productIdsBuilder.append(",");
            }
            productIdsBuilder.append(productId);

            // Calling update stock method from the InventoryDAOImplementation class
            repository.updatedStock(productId, countOfProduct - quantity);
            // Add the result for each item to the list
            Map<String, Object> result = new HashMap<>();
            result.put("name", name);
            result.put("price", price);
            result.put("quantity", quantity);
            resultList.add(result);
        }

        InitialBalanceAndPurchaseHistory currentTransaction = new InitialBalanceAndPurchaseHistory(0, transactionId,  LocalDateTime.now(), inputAmount, change, newInitialBalance);
        OrderLine orderLine=new OrderLine(transactionId,billingCounter, productIdsBuilder.toString());
        initialBalanceDAOImp.saveTransaction(currentTransaction, inputAmount);
        // Update denomination counts in the database
        denominationService.updateDenominationCounts(denominationMap); ////////working on this method
         repository.save_orderDetails(orderLine);
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
    public TotalCostResult processPurchaseRequest(List<Integer> productIds,
                                             List<Integer> quantities,
                                             List<Integer> prices,
                                             List<Integer> countsOfProduct,
                                             List<String> names) {
         AtomicInteger totalCost = new AtomicInteger();

          List<PurchaseInputDTO> responseList = IntStream.range(0, quantities.size())
            .filter(i -> {
                Integer quantity = quantities.get(i);
                return quantity != null && quantity > 0;
            })
            .mapToObj(i -> {
                int productId = productIds.get(i);
                int price = prices.get(i);
                int countOfProduct = countsOfProduct.get(i);
                String name = names.get(i);

                PurchaseInputDTO purchaseInputDTO = PurchaseInputDTO.builder()
                        .withProductId(productId)
                        .withPrice(price)
                        .withCountOfProduct(countOfProduct)
                        .withQuantity(quantities.get(i))
                        .withName(name)
                        .build();

                int individualCost = productCostCalculation(purchaseInputDTO);
                totalCost.addAndGet(individualCost);
                return purchaseInputDTO;
            })
            .collect(Collectors.toList());

          return new TotalCostResult(responseList, totalCost.get());
}


    public  List<PurchaseInputDTO> finalPurchaseRequest(List<Integer> productIds,
                                                          List<Integer> quantities,
                                                          List<Integer> prices,
                                                          List<Integer> countsOfProduct,
                                                          List<String> names) {

        List<PurchaseInputDTO> responseList = IntStream.range(0, productIds.size())
                .mapToObj(i -> PurchaseInputDTO.builder()
                        .withProductId(productIds.get(i))
                        .withPrice(prices.get(i))
                        .withCountOfProduct(countsOfProduct.get(i))
                        .withQuantity(quantities.get(i))
                        .withName(names.get(i))
                        .build())
                .collect(Collectors.toList());


        return responseList;
    }

    public int productCostCalculation(final PurchaseInputDTO purchaseInputDTOList) {
        return purchaseInputDTOList.getPrice() * purchaseInputDTOList.getQuantity();
    }
}
