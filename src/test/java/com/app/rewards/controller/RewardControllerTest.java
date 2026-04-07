package com.app.rewards.controller;

import com.app.rewards.dto.RewardResponse;
import com.app.rewards.service.RewardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RewardController.class)
class RewardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardService rewardService;

    @Test
    void shouldReturnRewardsSuccessfully() throws Exception {

        RewardResponse response = RewardResponse.builder()
                .customerId(1L)
                .customerName("John")
                .totalRewards(100)
                .build();

        when(rewardService.calculateRewards(anyLong(), any(), any()))
                .thenReturn(response);

        mockMvc.perform(get("/api/rewards/1")
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2024-03-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.customerName").value("John"))
                .andExpect(jsonPath("$.totalRewards").value(100));

        verify(rewardService, times(1))
                .calculateRewards(anyLong(), any(), any());
    }

    @Test
    void shouldReturnBadRequestForInvalidDateRange() throws Exception {

        mockMvc.perform(get("/api/rewards/1")
                        .param("startDate", "2025-01-01")
                        .param("endDate", "2024-01-01"))
                .andExpect(status().isBadRequest());
    }
}