package com.VendingMachine.model;


import java.time.LocalDateTime;

public class InitialBalanceAndPurchaseHistory {

    private int id;

    private int order_id;
    private LocalDateTime order_time;

    private int customerInputAmount;
    private int balanceAmount;

    private int vendingMachineBalance;

    public InitialBalanceAndPurchaseHistory(int id,int order_id, LocalDateTime order_time, int customerInputAmount, int balanceAmount, int vendingMachineBalance) {
        this.id = id;
        this.order_id=order_id;
        this.order_time = order_time;
        this.customerInputAmount = customerInputAmount;
        this.balanceAmount = balanceAmount;
        this.vendingMachineBalance = vendingMachineBalance;
    }
    //////////////////changes for purchase history///////////////

    public InitialBalanceAndPurchaseHistory() {
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public LocalDateTime getOrder_time() {
        return order_time;
    }


    public int getCustomerInputAmount() {
        return customerInputAmount;
    }


    public int getBalanceAmount() {
        return balanceAmount;
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
    public void setBalanceAmount(int balanceAmount) {this.balanceAmount = balanceAmount;}
    public void setVendingMachineBalance(int vendingMachineBalance) {
        this.vendingMachineBalance = vendingMachineBalance;
    }

    public void setOrder_time(LocalDateTime order_time) {
        this.order_time = order_time;
    }
}
