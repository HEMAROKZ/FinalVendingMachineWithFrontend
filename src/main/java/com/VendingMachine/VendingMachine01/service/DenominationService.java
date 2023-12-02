package com.VendingMachine.VendingMachine01.service;

import com.VendingMachine.VendingMachine01.customeexception.InsufficientInputCashException;
import com.VendingMachine.VendingMachine01.dao.DenominationDAO;
import com.VendingMachine.VendingMachine01.model.Denomination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class DenominationService {


    private final DenominationDAO denominationRepository;
    private static Logger log = LoggerFactory.getLogger(InventoryService.class);

    public DenominationService(DenominationDAO denominationRepository) {
        this.denominationRepository = denominationRepository;
    }

    void updateDenominationCounts(Map<Integer, Integer> denominationMap) {
        for (Map.Entry<Integer, Integer> entry : denominationMap.entrySet()) {
            int denomination = entry.getKey();
            int count = entry.getValue();
            updateDenominationCounts(count, denomination);
        }
    }


    ////must use null check must be handled
    private void updateDenominationCounts(int count, int denomination) {
        var optionalDenomination = denominationRepository.findById(1);
        if (optionalDenomination.isPresent()) {
            Denomination denominationEntity = optionalDenomination.get();
            switch (denomination) {
                case 50 -> denominationEntity.setFiftyRupee(denominationEntity.getFiftyRupee() - count);
                case 20 -> denominationEntity.setTwentyRupee(denominationEntity.getTwentyRupee() - count);
                case 10 -> denominationEntity.setTenRupee(denominationEntity.getTenRupee() - count);
                case 5 -> denominationEntity.setFiveRupee(denominationEntity.getFiveRupee() - count);
                case 2 -> denominationEntity.setTwoRupee(denominationEntity.getTwoRupee() - count);
                case 1 -> denominationEntity.setOneRupee(denominationEntity.getOneRupee() - count);
                default -> throw new IllegalArgumentException("Invalid denomination: " + denomination);
            }

            denominationRepository.update(denominationEntity);
        } else {
            throw new RuntimeException("Denomination not found for index 1");
        }

    }

    ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////

    private boolean isDenominationSufficient(Map<Integer, Integer> denominationMap, double totalPrice) {
        double totalDenominationAmount = 0;

        for (Map.Entry<Integer, Integer> entry : denominationMap.entrySet()) {
            int denomination = entry.getKey();
            int count = entry.getValue();
            totalDenominationAmount += denomination * count;
        }

        return totalDenominationAmount >= totalPrice;
    }
    ///////////////////////////////////////////////////////
    public int totalDenominationAmount(Map<Integer, Integer> denominationMap, double totalPrice){
      int totalDenominationAmount=0;
        for (Map.Entry<Integer, Integer> entry : denominationMap.entrySet()) {
            int denomination = entry.getKey();
            int count = entry.getValue();
            totalDenominationAmount += denomination * count;
        }
        return totalDenominationAmount;
    }
    ///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////


    public void addUpdateDenominationCounts(Map<Integer, Integer> denominationMap, double totalPrice) {
        // Check if the total denomination amount is sufficient
        if (isDenominationSufficient(denominationMap, totalPrice)) {
            for (Map.Entry<Integer, Integer> entry : denominationMap.entrySet()) {
                int denomination = entry.getKey();
                int count = entry.getValue();
                addUpdateDenominationCounts(count, denomination);
            }
        }else {
            throw new InsufficientInputCashException("Insufficient denomination amount. Please provide enough denominations.");
        }
    }

    private void addUpdateDenominationCounts(int count, int denomination) {
        var optionalDenomination = denominationRepository.findById(1);
        log.info("in addUpdateDenominationCounts METHOD");

        if (optionalDenomination.isPresent()) {
            Denomination denominationEntity = optionalDenomination.get();
            switch (denomination) {
                case 50 -> denominationEntity.setFiftyRupee(denominationEntity.getFiftyRupee() + count);
                case 20 -> denominationEntity.setTwentyRupee(denominationEntity.getTwentyRupee() + count);
                case 10 -> denominationEntity.setTenRupee(denominationEntity.getTenRupee() + count);
                case 5 -> denominationEntity.setFiveRupee(denominationEntity.getFiveRupee() + count);
                case 2 -> denominationEntity.setTwoRupee(denominationEntity.getTwoRupee() + count);
                case 1 -> denominationEntity.setOneRupee(denominationEntity.getOneRupee() + count);
                default -> throw new InsufficientInputCashException("Invalid denomination: " + denomination);
            }

            denominationRepository.update(denominationEntity);
        } else {
            throw new RuntimeException("Denomination not found for index 1");
        }
    }


/////////////////////////////////////////////////////////////////
    public Map<Integer, Integer> getCustomDenominationsFromDatabase() {
        Optional<Denomination> optionalDenomination = denominationRepository.findById(1);

        if (optionalDenomination.isPresent()) {
            Denomination denomination = optionalDenomination.get();

            Map<Integer, Integer> customDenominations = new HashMap<>();
            customDenominations.put(50, denomination.getFiftyRupee());
            customDenominations.put(20, denomination.getTwentyRupee());
            customDenominations.put(10, denomination.getTenRupee());
            customDenominations.put(5, denomination.getFiveRupee());
            customDenominations.put(2, denomination.getTwoRupee());
            customDenominations.put(1, denomination.getOneRupee());

            return customDenominations;
        } else {
            throw new RuntimeException("Denomination not found for index 1");
        }
    }


    public  Map<Integer, Integer> calculateCustomChangeDenominations(int amount, Map<Integer, Integer> customDenominations) {
        Map<Integer, Integer> denominationMap = new HashMap<>();
        final int[] remainingAmount = {amount};

        // Sort the denominations in descending order
        customDenominations.entrySet().stream()
                //before as it is
              //  .sorted((entry1, entry2) -> Integer.compare(entry2.getKey(), entry1.getKey()))

                .sorted(Map.Entry.<Integer,Integer>comparingByKey().reversed())
                .forEach(entry -> {
                    int denomination = entry.getKey();
                    int count = Math.min(remainingAmount[0] / denomination, entry.getValue());
                    denominationMap.put(denomination, count);
                    remainingAmount[0] -= denomination * count;
                });

        if (remainingAmount[0] > 0 && !canFormRemainingAmount(remainingAmount[0], customDenominations)) {
            System.out.println("No exact change available. Please provide the exact amount.");
            System.exit(0);
        }

        return denominationMap;
    }

    public  boolean canFormRemainingAmount(int remainingAmount, Map<Integer, Integer> customDenominations) {
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

    public  boolean isExactChangeAvailable(int change, Map<Integer, Integer> denominationMap) {
        int totalAvailableChange = denominationMap.entrySet().stream()
                .mapToInt(entry -> entry.getKey() * entry.getValue())
                .sum();

        return totalAvailableChange == change;
    }
}
