package com.VendingMachine.VendingMachine01.dao;

import com.VendingMachine.VendingMachine01.dto.controllerDTO.DenominationType;
import com.VendingMachine.VendingMachine01.model.Denomination;

import java.util.List;
import java.util.Optional;

public interface DenominationDAO {

     List<Denomination> getAllDenominations();

     void updateDenomination(Denomination denomination);
     Denomination getDenominationByDenominationType(DenominationType denominationType);

}
