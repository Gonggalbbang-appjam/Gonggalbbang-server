package com.example.gonggalbbang.domain.auth.presentation.dto.request;

import com.example.gonggalbbang.domain.auth.domain.entity.User;
import com.example.gonggalbbang.domain.auth.domain.enums.UserType;

public record JoinReq(String username, String name, String password, int number) {

    public static User fromJoinReq(JoinReq joinReq, String password) {
        return User.builder()
                .username(joinReq.username)
                .name(joinReq.name)
                .password(password)
                .role(UserType.ROLE_USER) // 학생 권한 추가
                .build();
    }
}
