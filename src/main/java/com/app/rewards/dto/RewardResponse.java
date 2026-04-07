package com.app.rewards.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class RewardResponse {
    private Long customerId;
    private String customerName;

    private LocalDate startDate;
    private LocalDate endDate;

    private Map<Integer, YearlyReward> yearlyRewards;

    private Integer totalRewards;
}