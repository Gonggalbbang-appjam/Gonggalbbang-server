package com.example.gonggalbbang.domain.auth.presentation.dto;

import lombok.Builder;
import com.example.gonggalbbang.domain.auth.domain.entity.User;
import com.example.gonggalbbang.domain.auth.domain.enums.UserType;

@Builder
public record UserVO(String username, String name, String password, UserType role) {

    public static UserVO fromUser(User user) {
        return UserVO.builder()
                .username(user.getUsername())
                .name(user.getName())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public User toUserEntity() {
        return User.builder()
                .username(this.username)
                .name(this.name)
                .password(this.password)
                .role(this.role)
                .build();
    }
}
