package com.example.gonggalbbang.domain.auth.domain.entity;

import com.example.gonggalbbang.domain.auth.domain.enums.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@Table(name = "tb_user")
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @Column(nullable = false, unique = true)
    private String username; // 아이디

    @Column(nullable = false)
    private String name; // 이름

    @Column(nullable = false)
    private String password; // 비번

    @Column(nullable = false)
    private int number; // 학번

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType role;
}
