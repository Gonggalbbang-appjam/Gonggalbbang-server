package com.example.gonggalbbang.domain.auth.presentation.dto.response;

import lombok.Builder;

@Builder
public record RefreshRes(String accessToken) {
}
