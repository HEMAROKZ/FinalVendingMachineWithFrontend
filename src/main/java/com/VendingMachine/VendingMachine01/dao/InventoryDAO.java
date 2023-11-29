package com.VendingMachine.VendingMachine01.dao;

import com.VendingMachine.VendingMachine01.dto.InventoryDTO;
import com.VendingMachine.VendingMachine01.model.Inventry;

import java.util.List;

public interface InventoryDAO {

   public List<Inventry> findAll();

//   public List<Inventry.InventoryBuilder> findById(int productId);
   public List<Inventry> findById(int productId);
   public int deleteById(int productId);

//   public int save(Inventry.InventoryBuilder e);
   public int save(InventoryDTO e);

//   public int update(Inventry e, int productId);

//   public int update(InventoryDTO e);

     public int update(Inventry e);

   public int updatedStock(int productId, int productInventryCount) ;

   public List<Inventry> findByInventryCount(int productInventryCount);
}
