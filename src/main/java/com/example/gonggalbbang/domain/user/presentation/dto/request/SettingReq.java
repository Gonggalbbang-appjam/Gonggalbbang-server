package com.example.gonggalbbang.domain.user.presentation.dto.request;

import com.example.gonggalbbang.domain.user.domain.enums.UpType;

public record SettingReq(UpType upType, String favorite, String payment) {
}
