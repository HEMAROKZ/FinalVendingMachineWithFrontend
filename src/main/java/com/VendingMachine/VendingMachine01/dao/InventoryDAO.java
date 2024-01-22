package com.VendingMachine.VendingMachine01.dao;

import com.VendingMachine.VendingMachine01.dto.InventoryDTO;
import com.VendingMachine.VendingMachine01.model.Inventry;
import com.VendingMachine.VendingMachine01.model.OrderLine;

import java.util.List;

public interface InventoryDAO {

    List<Inventry> findAll();

    List<Inventry> findById(int productId);

    void deleteById(int productId);

    int save(InventoryDTO e);

    int update(Inventry e);

    void updatedStock(int productId, int productInventryCount) ;
    int save_orderDetails( OrderLine orderLine);

}
