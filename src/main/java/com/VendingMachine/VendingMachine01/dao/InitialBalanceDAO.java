package com.VendingMachine.VendingMachine01.dao;

import com.VendingMachine.VendingMachine01.model.InitialBalanceAndPurchaseHistory;

import java.util.List;

public interface InitialBalanceDAO {

    InitialBalanceAndPurchaseHistory getChange();

    List<InitialBalanceAndPurchaseHistory> getAllPurchaseHistory();

    void saveTransaction(InitialBalanceAndPurchaseHistory initialBalanceAndPurchaseHistory);

    void initialBalanceUpdate (int customerInputAmount);

}
