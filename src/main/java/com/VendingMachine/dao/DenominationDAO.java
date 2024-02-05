package com.VendingMachine.dao;

import com.VendingMachine.dto.controllerDTO.DenominationType;
import com.VendingMachine.model.Denomination;

import java.util.List;

public interface DenominationDAO {

     List<Denomination> getAllDenominations();

     void updateDenomination(Denomination denomination);
     Denomination getDenominationByDenominationType(DenominationType denominationType);

}
