package com.app.rewards.service;

import com.app.rewards.dto.RewardResponse;
import com.app.rewards.dto.YearlyReward;
import com.app.rewards.exception.ResourceNotFoundException;
import com.app.rewards.model.Customer;
import com.app.rewards.model.Transaction;
import com.app.rewards.repository.CustomerRepo;
import com.app.rewards.repository.TransactionRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RewardService {

    private final TransactionRepo transactionRepository;
    private final CustomerRepo customerRepository;

    public RewardResponse calculateRewards(Long customerId,
                                           LocalDate startDate,
                                           LocalDate endDate) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        List<Transaction> transactions = transactionRepository
                .findByCustomerIdAndDateBetween(customerId, startDate, endDate);

        if (transactions.isEmpty()) {
            throw new ResourceNotFoundException("No transactions found");
        }

        // Step 1: Group by Year -> Month
        Map<Integer, Map<String, Integer>> grouped =
                transactions.stream()
                        .collect(Collectors.groupingBy(
                                t -> t.getDate().getYear(),
                                TreeMap::new,
                                Collectors.groupingBy(
                                        t -> String.format("%02d", t.getDate().getMonthValue()),
                                        TreeMap::new,
                                        Collectors.summingInt(t -> calculatePoints(t.getAmount()))
                                )
                        ));

        // Step 2: Build YearlyRewards
        Map<Integer, YearlyReward> yearlyRewards = new TreeMap<>();
        int totalPoints = 0;

        for (Map.Entry<Integer, Map<String, Integer>> entry : grouped.entrySet()) {
            int year = entry.getKey();
            Map<String, Integer> monthly = entry.getValue();

            int yearlyTotal = monthly.values()
                    .stream()
                    .mapToInt(Integer::intValue)
                    .sum();

            totalPoints += yearlyTotal;

            yearlyRewards.put(year, new YearlyReward(monthly, yearlyTotal));
        }

        // Step 3: Build Response
        return RewardResponse.builder()
                .customerId(customer.getId())
                .customerName(customer.getName())
                .startDate(startDate)
                .endDate(endDate)
                .yearlyRewards(yearlyRewards)
                .totalRewards(totalPoints)
                .build();
    }

    private int calculatePoints(double amount) {
        if (amount <= 50) return 0;
        else if (amount <= 100) return (int) (amount - 50);
        else return (int) ((amount - 100) * 2 + 50);
    }
}