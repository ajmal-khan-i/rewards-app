package com.app.rewards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class YearlyReward {
    private Map<String, Integer> monthly;
    private Integer total;
}