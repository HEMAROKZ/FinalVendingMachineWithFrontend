package com.VendingMachine.VendingMachine01.dao;

import com.VendingMachine.VendingMachine01.model.Denomination;

import java.util.List;
import java.util.Optional;

public interface DenominationDAO {
    public Optional<Denomination> findById(int indexId);
    public void update(Denomination denomination) ;

}
