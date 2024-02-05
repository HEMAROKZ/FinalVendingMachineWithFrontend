package com.VendingMachine.dao;

import com.VendingMachine.dto.InventoryDTO;
import com.VendingMachine.model.Inventry;
import com.VendingMachine.model.OrderLine;

import java.util.List;

public interface InventoryDAO {

    List<Inventry> findAll();

    List<Inventry> findById(int productId);

    int deleteById(int productId);

    int save(InventoryDTO e);

    int update(Inventry e);

    void updatedStock(int productId, int productInventryCount) ;
    int save_orderDetails( OrderLine orderLine);
    void deleteRowsWithOrderIdZero();
    void mergeOrderLineRows();

    void createOrderLineMergedTable();

}
