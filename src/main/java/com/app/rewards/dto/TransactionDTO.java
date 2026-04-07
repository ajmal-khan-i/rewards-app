package com.app.rewards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TransactionDTO {
    private Double amount;
    private LocalDate date;
    private Integer points;
}