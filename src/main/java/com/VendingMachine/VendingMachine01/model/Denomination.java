package com.VendingMachine.VendingMachine01.model;


public class Denomination {
    private int indexId;
    private int fiftyRupee;
    private int twentyRupee;

    private int tenRupee;

    private int fiveRupee;

    private int twoRupee;

    private int oneRupee;



    public Denomination(int indexId, int fiftyRupee, int twentyRupee, int tenRupee, int fiveRupee, int twoRupee, int oneRupee) {
        this.indexId = indexId;
        this.fiftyRupee = fiftyRupee;
        this.twentyRupee = twentyRupee;
        this.tenRupee = tenRupee;
        this.fiveRupee = fiveRupee;
        this.twoRupee = twoRupee;
        this.oneRupee = oneRupee;

    }

    public Denomination() {
    }

    public int getIndexId() {
        return indexId;
    }
    public void setIndexId(int indexId) {
        this.indexId = indexId;
    }


    public int getFiftyRupee() {
        return fiftyRupee;
    }

    public void setFiftyRupee(int fiftyRupee) {
        this.fiftyRupee = fiftyRupee;
    }

    public int getTwentyRupee() {
        return twentyRupee;
    }

    public void setTwentyRupee(int twentyRupee) {
        this.twentyRupee = twentyRupee;
    }

    public int getTenRupee() {
        return tenRupee;
    }

    public void setTenRupee(int tenRupee) {
        this.tenRupee = tenRupee;
    }

    public int getFiveRupee() {
        return fiveRupee;
    }

    public void setFiveRupee(int fiveRupee) {
        this.fiveRupee = fiveRupee;
    }

    public int getTwoRupee() {
        return twoRupee;
    }

    public void setTwoRupee(int twoRupee) {
        this.twoRupee = twoRupee;
    }

    public int getOneRupee() {
        return oneRupee;
    }

    public void setOneRupee(int oneRupee) {
        this.oneRupee = oneRupee;
    }
}
