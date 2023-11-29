package com.VendingMachine.VendingMachine01.model;


public class InitialBalanceAndPurchaseHistory {

    private int id;
    private int productId;
    private String product;
    private int productPrice;
    private int customerInputAmount;
    private int vendingMachineBalance;

    private int initialBalance;

    public InitialBalanceAndPurchaseHistory(int id, int productId, String product, int productPrice, int customerInputAmount, int vendingMachineBalance, int initialBalance) {
        this.id = id;
        this.productId = productId;
        this.product = product;
        this.productPrice = productPrice;
        this.customerInputAmount = customerInputAmount;
        this.vendingMachineBalance = vendingMachineBalance;
        this.initialBalance = initialBalance;
    }



    public InitialBalanceAndPurchaseHistory() {
    }

    public int getProductId() {
        return productId;
    }


    public String getProduct() {
        return product;
    }


    public int getProductPrice() {
        return productPrice;
    }


    public int getCustomerInputAmount() {
        return customerInputAmount;
    }


    public int getVendingMachineBalance() {
        return vendingMachineBalance;
    }



    public int getId() {
        return id;
    }


    public int getInitialBalance() {
        return initialBalance;
    }

    public void setCustomerInputAmount(int customerInputAmount) {
        this.customerInputAmount = customerInputAmount;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setVendingMachineBalance(int vendingMachineBalance) {this.vendingMachineBalance = vendingMachineBalance;}
    public void setInitialBalance(int initialBalance) {
        this.initialBalance = initialBalance;
    }
    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }
    public void setProduct(String product) {
        this.product = product;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
}
