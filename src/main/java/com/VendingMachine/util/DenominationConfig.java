package com.VendingMachine.util;

import com.VendingMachine.dto.controllerDTO.DenominationType;

import java.util.Map;

public class DenominationConfig {
    private int denomination50;
    private int denomination20;
    private int denomination10;
    private int denomination5;
    private int denomination2;
    private int denomination1;

    public DenominationConfig() {
        // Default constructor
    }

    public int getDenomination50() {
        return denomination50;
    }

    public void setDenomination50(int denomination50) {
        this.denomination50 = denomination50;
    }

    public int getDenomination20() {
        return denomination20;
    }

    public void setDenomination20(int denomination20) {
        this.denomination20 = denomination20;
    }

    public int getDenomination10() {
        return denomination10;
    }

    public void setDenomination10(int denomination10) {
        this.denomination10 = denomination10;
    }

    public int getDenomination5() {
        return denomination5;
    }

    public void setDenomination5(int denomination5) {
        this.denomination5 = denomination5;
    }

    public int getDenomination2() {
        return denomination2;
    }

    public void setDenomination2(int denomination2) {
        this.denomination2 = denomination2;
    }

    public int getDenomination1() {
        return denomination1;
    }

    public void setDenomination1(int denomination1) {
        this.denomination1 = denomination1;
    }

    @Override
    public String toString() {
        return "DenominationConfig{" +
                "denomination50=" + denomination50 +
                ", denomination20=" + denomination20 +
                ", denomination10=" + denomination10 +
                ", denomination5=" + denomination5 +
                ", denomination2=" + denomination2 +
                ", denomination1=" + denomination1 +
                '}';
    }

    public Map<DenominationType, Integer> getDenominationValues() {
        // Create a Map from the individual denominations
        Map<DenominationType, Integer> denominationMap = Map.of(
                DenominationType.FIFTY_RUPEE, denomination50,
                DenominationType.TWENTY_RUPEE, denomination20,
                DenominationType.TEN_RUPEE, denomination10,
                DenominationType.FIVE_RUPEE, denomination5,
                DenominationType.TWO_RUPEE, denomination2,
                DenominationType.ONE_RUPEE, denomination1
        );
        return denominationMap;


    }
}
