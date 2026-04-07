package com.app.rewards.dto;

import java.util.List;
import java.util.Map;

public class RewardDTO {
    private Long customerId;
    private String customerName;
    private Map<String, Integer> monthlyRewards;
    private Integer totalRewards;
    private List<TransactionDTO> transactions;
}
