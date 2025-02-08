package com.example.gonggalbbang.domain.history.presentation.dto.response;

import com.example.gonggalbbang.domain.history.domain.entity.History;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record HistoryRes(
    String username,
    String name,
    LocalDate donationDate,
    Integer amount
) {
    public static HistoryRes from(History history) {
        return HistoryRes.builder()
                .username(history.getUser().getUsername())
                .name(history.getUser().getName())
                .donationDate(history.getDonationDate())
                .amount(history.getAmount())
                .build();
    }
} 