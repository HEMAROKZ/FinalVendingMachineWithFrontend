package com.VendingMachine.VendingMachine01.dao;

import com.VendingMachine.VendingMachine01.model.InitialBalanceAndPurchaseHistory;

import java.util.List;

public interface InitialBalanceDAO {

    InitialBalanceAndPurchaseHistory getChange();

    List<InitialBalanceAndPurchaseHistory> getAllPurchaseHistory();

    int saveTransaction(InitialBalanceAndPurchaseHistory initialBalanceAndPurchaseHistory);

    int initialBalanceUpdate (int customerInputAmount);

}
