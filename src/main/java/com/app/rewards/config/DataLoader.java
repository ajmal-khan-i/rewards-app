package com.app.rewards.config;

import com.app.rewards.model.Customer;
import com.app.rewards.model.Transaction;
import com.app.rewards.repository.CustomerRepo;
import com.app.rewards.repository.TransactionRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(CustomerRepo customerRepo,
                               TransactionRepo transactionRepo) {

        return args -> {

            Customer c1 = customerRepo.save(new Customer(null, "John"));
            Customer c2 = customerRepo.save(new Customer(null, "Alice"));
            Customer c3 = customerRepo.save(new Customer(null, "Bob"));
            Customer c4 = customerRepo.save(new Customer(null, "EmptyUser"));

            // =========================================================
            // Customer 1 → MULTI-YEAR + MULTIPLE TRANSACTIONS PER MONTH
            // Purpose:
            // - Test grouping (Year → Month)
            // - Test aggregation (multiple txns per month)
            // - Test cross-year range
            // =========================================================

            // Nov 2024
            transactionRepo.save(new Transaction(null, 120.0, LocalDate.of(2024,11,10), c1)); // 90
            transactionRepo.save(new Transaction(null, 60.0, LocalDate.of(2024,11,15), c1));  // 10

            // Dec 2024
            transactionRepo.save(new Transaction(null, 80.0, LocalDate.of(2024,12,5), c1));   // 30
            transactionRepo.save(new Transaction(null, 200.0, LocalDate.of(2024,12,20), c1)); // 250

            // Jan 2025
            transactionRepo.save(new Transaction(null, 75.0, LocalDate.of(2025,1,10), c1));   // 25
            transactionRepo.save(new Transaction(null, 150.0, LocalDate.of(2025,1,25), c1));  // 150

            // Feb 2025
            transactionRepo.save(new Transaction(null, 40.0, LocalDate.of(2025,2,5), c1));    // 0
            transactionRepo.save(new Transaction(null, 110.0, LocalDate.of(2025,2,18), c1));  // 70


            // =========================================================
            // Customer 2 → HIGH VALUE TRANSACTIONS
            // Purpose:
            // - Validate reward calculation for large amounts
            // - Ensure aggregation handles big numbers
            // =========================================================

            // Jan 2024
            transactionRepo.save(new Transaction(null, 300.0, LocalDate.of(2024,1,5), c2));  // 450
            transactionRepo.save(new Transaction(null, 220.0, LocalDate.of(2024,1,18), c2)); // 290

            // Feb 2024
            transactionRepo.save(new Transaction(null, 150.0, LocalDate.of(2024,2,10), c2)); // 150
            transactionRepo.save(new Transaction(null, 130.0, LocalDate.of(2024,2,25), c2)); // 110

            // Mar 2024
            transactionRepo.save(new Transaction(null, 90.0, LocalDate.of(2024,3,12), c2));  // 40
            transactionRepo.save(new Transaction(null, 60.0, LocalDate.of(2024,3,28), c2));  // 10


            // =========================================================
            // Customer 3 → EDGE CASES (Boundary Values)
            // Purpose:
            // - Validate reward rules at boundaries
            // =========================================================

            // Jan 2024
            transactionRepo.save(new Transaction(null, 50.0, LocalDate.of(2024,1,1), c3));   // 0
            transactionRepo.save(new Transaction(null, 51.0, LocalDate.of(2024,1,10), c3));  // 1

            // Feb 2024
            transactionRepo.save(new Transaction(null, 100.0, LocalDate.of(2024,2,1), c3));  // 50
            transactionRepo.save(new Transaction(null, 101.0, LocalDate.of(2024,2,15), c3)); // 52

            // Mar 2024
            transactionRepo.save(new Transaction(null, 49.0, LocalDate.of(2024,3,1), c3));   // 0
            transactionRepo.save(new Transaction(null, 200.0, LocalDate.of(2024,3,20), c3)); // 250


            // =========================================================
            // Customer 4 → NO TRANSACTIONS
            // Purpose:
            // - Validate empty response / exception handling
            // =========================================================

            // (No transactions intentionally)
        };
    }
}