package com.VendingMachine.VendingMachine01.model;


public class InitialBalanceAndPurchaseHistory {

    private int id;

    private int transactionId;
    private String productId;

    private int customerInputAmount;
    private int changeAmount;

    private int vendingMachineBalance;

    public InitialBalanceAndPurchaseHistory(int id,int transactionId, String productId, int customerInputAmount, int changeAmount, int vendingMachineBalance) {
        this.id = id;
        this.transactionId=transactionId;
        this.productId = productId;
        this.customerInputAmount = customerInputAmount;
        this.changeAmount = changeAmount;
        this.vendingMachineBalance = vendingMachineBalance;
    }
    //////////////////changes for purchase history///////////////

    public InitialBalanceAndPurchaseHistory() {
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getProductId() {
        return productId;
    }


    public int getCustomerInputAmount() {
        return customerInputAmount;
    }


    public int getChangeAmount() {
        return changeAmount;
    }



    public int getId() {
        return id;
    }


    public int getVendingMachineBalance() {
        return vendingMachineBalance;
    }

    public void setCustomerInputAmount(int customerInputAmount) {
        this.customerInputAmount = customerInputAmount;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setChangeAmount(int changeAmount) {this.changeAmount = changeAmount;}
    public void setVendingMachineBalance(int vendingMachineBalance) {
        this.vendingMachineBalance = vendingMachineBalance;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
