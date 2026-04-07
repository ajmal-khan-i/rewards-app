package com.app.rewards.service;

import com.app.rewards.dto.RewardResponse;
import com.app.rewards.exception.ResourceNotFoundException;
import com.app.rewards.model.Customer;
import com.app.rewards.model.Transaction;
import com.app.rewards.repository.CustomerRepo;
import com.app.rewards.repository.TransactionRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RewardServiceTest {

    @Mock
    private TransactionRepo transactionRepo;

    @Mock
    private CustomerRepo customerRepo;

    @InjectMocks
    private RewardService rewardService;

    @Test
    void shouldCalculateRewardsCorrectly() {

        Customer customer = new Customer(1L, "John");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(1L, 120.0, LocalDate.of(2024,1,10), customer),
                new Transaction(2L, 75.0, LocalDate.of(2024,2,10), customer),
                new Transaction(3L, 45.0, LocalDate.of(2024,3,10), customer)
        );

        when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));
        when(transactionRepo.findByCustomerIdAndDateBetween(
                eq(1L),any(),any())).thenReturn(transactions);

        RewardResponse response = rewardService.calculateRewards(
                1L,
                LocalDate.of(2024,1,1),
                LocalDate.of(2024,3,31));

        assertNotNull(response);
        assertEquals(1L, response.getCustomerId());
        assertEquals("John", response.getCustomerName());
        assertTrue(response.getTotalRewards() > 0);
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {

        when(customerRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> rewardService.calculateRewards(1L, null, null));
    }

    @Test
    void shouldThrowExceptionWhenNoTransactions() {

        Customer customer = new Customer(1L, "John");

        when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> rewardService.calculateRewards(1L, null, null));

        assertEquals("No transactions found", ex.getMessage());
    }
}