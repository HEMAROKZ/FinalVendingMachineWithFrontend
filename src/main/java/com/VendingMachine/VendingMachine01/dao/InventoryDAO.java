package com.VendingMachine.VendingMachine01.dao;

import com.VendingMachine.VendingMachine01.dto.InventoryDTO;
import com.VendingMachine.VendingMachine01.model.Inventry;

import java.util.List;

public interface InventoryDAO {

    List<Inventry> findAll();

    List<Inventry> findById(int productId);
    int deleteById(int productId);

    int save(InventoryDTO e);


      int update(Inventry e);

    void updatedStock(int productId, int productInventryCount) ;

//    List<Inventry> findByInventryCount(int productInventryCount);
}
