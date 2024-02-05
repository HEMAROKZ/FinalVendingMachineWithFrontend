package com.VendingMachine.dao;

import com.VendingMachine.model.InitialBalanceAndPurchaseHistory;

import java.util.List;

public interface InitialBalanceDAO {

    InitialBalanceAndPurchaseHistory getChange();

    List<InitialBalanceAndPurchaseHistory> getAllPurchaseHistory();

    void saveTransaction(InitialBalanceAndPurchaseHistory initialBalanceAndPurchaseHistory,int customerInputAmount);

}
