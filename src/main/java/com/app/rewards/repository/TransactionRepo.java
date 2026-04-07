package com.app.rewards.repository;

import com.app.rewards.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    List<Transaction> findByCustomerId(Long customerId);

    List<Transaction> findByCustomerIdAndDateBetween(
            Long customerId, LocalDate startDate, LocalDate endDate);
}
