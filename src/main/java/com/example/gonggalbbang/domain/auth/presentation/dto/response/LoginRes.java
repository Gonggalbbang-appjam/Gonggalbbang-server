package com.example.gonggalbbang.domain.auth.presentation.dto.response;

import lombok.Builder;

@Builder
public record LoginRes(String refreshToken, String accessToken) {
}
