package com.VendingMachine.dto.controllerDTO;
public enum DenominationType {
    FIFTY_RUPEE(50),
    TWENTY_RUPEE(20),
    TEN_RUPEE(10),
    FIVE_RUPEE(5),
    TWO_RUPEE(2),
    ONE_RUPEE(1);



    private final int value;

    DenominationType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
