package com.VendingMachine.VendingMachine01.dao;

import com.VendingMachine.VendingMachine01.model.Denomination;

import java.util.Optional;

public interface DenominationDAO {
     Optional<Denomination> findById(int indexId);
     void update(Denomination denomination) ;

}
