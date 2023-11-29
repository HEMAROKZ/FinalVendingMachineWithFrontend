package com.VendingMachine.VendingMachine01.dao;

import com.VendingMachine.VendingMachine01.model.InitialBalanceAndPurchaseHistory;
import com.VendingMachine.VendingMachine01.model.Inventry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InitialBalanceDAOImp implements InitialBalanceDAO {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public InitialBalanceAndPurchaseHistory getChange() {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", getAllPurchaseHistory().size());

        List<InitialBalanceAndPurchaseHistory> initialBalanceAndPurchaseHistoryList = namedParameterJdbcTemplate.query("select initialBalance from purchasehistory_table where id = :id",sqlParameterSource, new BeanPropertyRowMapper<InitialBalanceAndPurchaseHistory>(InitialBalanceAndPurchaseHistory.class));
        return initialBalanceAndPurchaseHistoryList.get(0);
    }

    @Override
    public List<InitialBalanceAndPurchaseHistory> getAllPurchaseHistory() {
        return namedParameterJdbcTemplate.query("select * from purchasehistory_table ", new BeanPropertyRowMapper<InitialBalanceAndPurchaseHistory>(InitialBalanceAndPurchaseHistory.class));
    }


    //////////////////////////////////////////////////////////////////
    @Override
    public int saveTransaction(InitialBalanceAndPurchaseHistory initialBalanceAndPurchaseHistory) {

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", getAllPurchaseHistory().size() + 1)
                .addValue("productId", initialBalanceAndPurchaseHistory.getProductId())
                .addValue("product", initialBalanceAndPurchaseHistory.getProduct())
                .addValue("productPrice", initialBalanceAndPurchaseHistory.getProductPrice())
                .addValue("customerInputAmount", initialBalanceAndPurchaseHistory.getCustomerInputAmount())
                .addValue("vendingMachinebalance", initialBalanceAndPurchaseHistory.getVendingMachineBalance())
                .addValue("initialBalance", initialBalanceAndPurchaseHistory.getInitialBalance());

        namedParameterJdbcTemplate.getJdbcOperations().update("SET IDENTITY_INSERT purchasehistory_table ON");
        int rowsInserted = namedParameterJdbcTemplate.update("insert into purchasehistory_table (id,productId,product,productPrice,customerInputAmount,vendingMachinebalance,initialBalance) values (:id,:productId,:product,:productPrice,:customerInputAmount,:vendingMachinebalance,:initialBalance)", sqlParameterSource);
        namedParameterJdbcTemplate.getJdbcOperations().update("SET IDENTITY_INSERT purchasehistory_table OFF");
        return rowsInserted;
//        return namedParameterJdbcTemplate.update("insert into purchasehistory_table (id,productId,product,productPrice,customerInputAmount,vendingMachinebalance,initialBalance) values (:id,:productId,:product,:productPrice,:customerInputAmount,:vendingMachinebalance,:initialBalance)", sqlParameterSource);
    }
/////////////////////////////////
//
//    @Override
//    public int initialBalanceUpdate(int customerInputAmount) {
//        String product = "update_balance"; // Set product to "update_balance"
//        double initialBalance = customerInputAmount + getChange().getInitialBalance();
//
//        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", getAllPurchaseHistory().size() + 1)
//                .addValue("productId", 0)
//                .addValue("product", product)
//                .addValue("productPrice", 0)
//                .addValue("customerInputAmount", customerInputAmount)
//                .addValue("vendingMachinebalance", 0)
//                .addValue("initialBalance", initialBalance);
//
//        namedParameterJdbcTemplate.getJdbcOperations().update("SET IDENTITY_INSERT purchasehistory_table ON");
//        int rowsInserted = namedParameterJdbcTemplate.update("insert into purchasehistory_table (productId, product, productPrice, customerInputAmount, vendingMachinebalance, initialBalance) values (:productId, :product, :productPrice, :customerInputAmount, :vendingMachinebalance, :initialBalance)", sqlParameterSource);
//        namedParameterJdbcTemplate.getJdbcOperations().update("SET IDENTITY_INSERT purchasehistory_table OFF");
//
//        return rowsInserted;
//    }
    ///////////////////////////
@Override
public int initialBalanceUpdate(int customerInputAmount) {
    String product = "update_balance"; // Set product to "update_balance"
    int initialBalance = customerInputAmount + getChange().getInitialBalance();// Assuming getLatestInitialBalance() retrieves the latest initial balance

    SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", getAllPurchaseHistory().size() + 1)
            .addValue("productId", 10001)
            .addValue("product", product)
            .addValue("productPrice", 0)
            .addValue("customerInputAmount", customerInputAmount)
            .addValue("vendingMachinebalance", 0)
            .addValue("initialBalance", initialBalance);

    namedParameterJdbcTemplate.getJdbcOperations().update("SET IDENTITY_INSERT purchasehistory_table ON");
    int rowsInserted = namedParameterJdbcTemplate.update("insert into purchasehistory_table (id,productId,product,productPrice,customerInputAmount,vendingMachinebalance,initialBalance) values (:id,:productId,:product,:productPrice,:customerInputAmount,:vendingMachinebalance,:initialBalance)", sqlParameterSource);
    namedParameterJdbcTemplate.getJdbcOperations().update("SET IDENTITY_INSERT purchasehistory_table OFF");
    return rowsInserted;
}


}
