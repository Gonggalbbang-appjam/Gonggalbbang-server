package com.example.gonggalbbang.domain.auth.presentation.dto;

import lombok.Builder;
import com.example.gonggalbbang.domain.auth.domain.entity.User;
import com.example.gonggalbbang.domain.auth.domain.enums.UserType;

@Builder
public record UserVO(String username, String name, String password, int number, UserType role) {

    public static UserVO fromUser(User user) {
        return UserVO.builder()
                .username(user.getUsername())
                .name(user.getName())
                .password(user.getPassword())
                .number(user.getNumber())
                .role(user.getRole())
                .build();
    }

    public User toUserEntity() {
        return User.builder()
                .username(this.username)
                .name(this.name)
                .password(this.password)
                .number(this.number)
                .role(this.role)
                .build();
    }
}
