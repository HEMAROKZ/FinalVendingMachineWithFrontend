package com.VendingMachine.dto;

import java.util.Map;

public class VendingMachineOutputDTO {

    private final String item;
    private final int price;
    private final int balance;
    private final Map<Integer, Integer> denominations;

    public VendingMachineOutputDTO(String item, int price, int balance, Map<Integer, Integer> denominations) {
        this.item = item;
        this.price = price;
        this.balance = balance;
        this.denominations = denominations;
    }
    public VendingMachineOutputDTO(VendingMachineOutputDTOBuilder vendingMachineOutputDTOBuilder) {
        this.item = vendingMachineOutputDTOBuilder.item;
        this.price = vendingMachineOutputDTOBuilder.price;
        this.balance = vendingMachineOutputDTOBuilder.balance;
        this.denominations = vendingMachineOutputDTOBuilder.denominations;
    }
    public String getItem() {
        return item;
    }

    public int getPrice() {
        return price;
    }

    public int getBalance() {
        return balance;
    }

    public Map<Integer, Integer> getDenominations() {
        return denominations;
    }

    public static class VendingMachineOutputDTOBuilder {
        private String item;
        private int price;
        private int balance;
        private Map<Integer, Integer> denominations;

        public VendingMachineOutputDTOBuilder() {
        }

        public VendingMachineOutputDTOBuilder balance(int balance) {
            this.balance = balance;
            return this;
        }

        public VendingMachineOutputDTOBuilder price(int price) {
            this.price = price;
            return this;
        }

        public VendingMachineOutputDTOBuilder item(String item) {
            this.item = item;
            return this;
        }

        public VendingMachineOutputDTOBuilder denominations(Map<Integer, Integer> denominations) {
            this.denominations = denominations;
            return this;
        }

        public VendingMachineOutputDTO build() {
            return new VendingMachineOutputDTO(this);
        }
    }
}
