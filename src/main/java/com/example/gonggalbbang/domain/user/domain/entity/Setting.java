package com.example.gonggalbbang.domain.user.domain.entity;

import com.example.gonggalbbang.domain.auth.domain.entity.User;
import com.example.gonggalbbang.domain.user.domain.enums.UpType;
import com.example.gonggalbbang.domain.user.presentation.dto.request.SettingReq;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@Entity
@Table(name = "tb_set")
@NoArgsConstructor
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UpType upType;

    @Column(nullable = false)
    private String favorite;

    @Column(nullable = false)
    private String payment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    public static Setting fromSettingReq(SettingReq settingReq, User user) {
        return Setting.builder()
                .upType(settingReq.upType())
                .favorite(settingReq.favorite())
                .payment(settingReq.payment())
                .user(user) // 사용자 추가
                .build();
    }

}
