package com.example.gonggalbbang.domain.auth.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType {

    ROLE_USER("USER");

    private final String role;
}
