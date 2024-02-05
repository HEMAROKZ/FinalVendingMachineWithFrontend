package com.VendingMachine.service;

import com.VendingMachine.customeexception.InsufficientInputCashException;
import com.VendingMachine.customeexception.NoExactChangeException;
import com.VendingMachine.customeexception.ProductUnavialableException;
import com.VendingMachine.dao.DenominationDAO;
import com.VendingMachine.dto.controllerDTO.DenominationType;
import com.VendingMachine.model.Denomination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DenominationService {

    private final DenominationDAO denominationRepository;
    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);

    @Autowired
    public DenominationService(DenominationDAO denominationRepository) {
        this.denominationRepository = denominationRepository;
    }

    final void updateDenominationCounts(Map<Integer, Integer> denominationMap) {
        for (Map.Entry<Integer, Integer> entry : denominationMap.entrySet()) {
            int denominationValue = entry.getKey();
            int count = entry.getValue();
            DenominationType denominationType = findDenominationTypeByValue(denominationValue);
            if (denominationType != null) {
                updateDenominationCounts(count, denominationType);
            } else {
                // Handle the case where the denomination type is not found
                throw new ProductUnavialableException("Denomination type not found for value: " + denominationValue);
            }
        }
    }

    private  DenominationType findDenominationTypeByValue(int value) {
        for (DenominationType denominationType : DenominationType.values()) {
            if (denominationType.getValue() == value) {
                log.info("in findDenominationTypeByValue ->denominationType------->"+denominationType);
                return denominationType;
            }
        }
        return null;
    }

    private void updateDenominationCounts(final int count, final DenominationType denominationType) {
        // Fetch the existing denomination from the database
        Denomination existingDenomination = denominationRepository.getDenominationByDenominationType(denominationType);
        // Update the count for the existing denomination
        existingDenomination.setCount(existingDenomination.getCount()-count);

        // Update the denomination in the database
        denominationRepository.updateDenomination(existingDenomination);
    }

    public void addUpdateDenominationCounts(final Map<DenominationType, Integer> denominationMap, final double totalPrice) {
        if (isDenominationSufficient(denominationMap, totalPrice)) {
            for (Map.Entry<DenominationType, Integer> entry : denominationMap.entrySet()) {
                DenominationType denomination = entry.getKey();
                int count = entry.getValue();
                addUpdateDenominationCounts(count, denomination);
            }
        } else {
            throw new InsufficientInputCashException("Insufficient denomination amount. Please provide enough denominations.");
        }
    }
    /////////////////////////////////////////////////////////////////////////
    private void addUpdateDenominationCounts(final int count, final DenominationType denominationType) {
        Denomination existingDenomination = denominationRepository.getDenominationByDenominationType(denominationType);

        // Update the count for the existing denomination
        existingDenomination.setCount(existingDenomination.getCount()+count);

        // Update the denomination in the database
        denominationRepository.updateDenomination(existingDenomination);
    }


    //   ////////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean isDenominationSufficient(final Map<DenominationType, Integer> denominationMap, final double totalPrice) {
        double totalDenominationAmount = 0;

        for (Map.Entry<DenominationType, Integer> entry : denominationMap.entrySet()) {
            int denomination = entry.getKey().getValue();
            int count = entry.getValue();
            totalDenominationAmount += denomination * count;
        }

        return totalDenominationAmount >= totalPrice;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    public int totalDenominationAmount(final Map<DenominationType, Integer> denominationMap, final double totalPrice) {
        int totalDenominationAmount = 0;
        for (Map.Entry<DenominationType, Integer> entry : denominationMap.entrySet()) {
            int denomination = entry.getKey().getValue();
            int count = entry.getValue();
            totalDenominationAmount += denomination * count;
        }
        return totalDenominationAmount;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////


    public final Map<Integer, Integer> getCustomDenominationsFromDatabase() {
        List<Denomination> denominations = denominationRepository.getAllDenominations();

        Map<Integer, Integer> customDenominations = new HashMap<>();

        for (Denomination denomination : denominations) {
            customDenominations.put(denomination.getDenominationType().getValue(), denomination.getCount());
        }
        return customDenominations;
    }


    public final Map<Integer, Integer> calculateCustomChangeDenominations(final int amount, final Map<Integer, Integer> customDenominations) {
        Map<Integer, Integer> denominationMap = new HashMap<>();
        final int[] remainingAmount = {amount};

        customDenominations.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByKey().reversed())
                .forEach(entry -> {
                    int denomination = entry.getKey();
                    int count = Math.min(remainingAmount[0] / denomination, entry.getValue());
                    denominationMap.put(denomination, count);
                    remainingAmount[0] -= denomination * count;
                });

        if (remainingAmount[0] > 0 && !canFormRemainingAmount(remainingAmount[0], customDenominations)) {
            throw new NoExactChangeException("No exact change available. Please provide the exact amount.");
        }

        return denominationMap;
    }

    public final boolean canFormRemainingAmount(final int remainingAmount, final Map<Integer, Integer> customDenominations) {
        for (Map.Entry<Integer, Integer> entry : customDenominations.entrySet()) {
            int denomination = entry.getKey();
            int count = entry.getValue();

            if (denomination <= remainingAmount) {
                int remainingCount = remainingAmount / denomination;
                if (remainingCount <= count) {
                    return true;
                }
            }
        }

        return false;
    }

    public final boolean isExactChangeAvailable(final int change, final Map<Integer, Integer> denominationMap) {
        int totalAvailableChange = denominationMap.entrySet().stream()
                .mapToInt(entry -> entry.getKey() * entry.getValue())
                .sum();

        return totalAvailableChange == change;
    }

}
