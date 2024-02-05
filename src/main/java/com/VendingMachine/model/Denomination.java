package com.VendingMachine.model;

import com.VendingMachine.dto.controllerDTO.DenominationType;

public class Denomination {
    private int id;
    private DenominationType denominationType;
    private int count;

    public Denomination() {
    }

    public Denomination(int id, DenominationType denominationType, int count) {
        this.id = id;
        this.denominationType = denominationType;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public DenominationType getDenominationType() {
        return denominationType;
    }

    public int getCount() {
        return count;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDenominationType(DenominationType denominationType) {
        this.denominationType = denominationType;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDenominationValue() {
        return denominationType.getValue();
    }

    @Override
    public String toString() {
        return "Denomination{" +
                "id=" + id +
                ", denominationType=" + denominationType.getValue() +
                ", count=" + count +
                '}';
    }
}

