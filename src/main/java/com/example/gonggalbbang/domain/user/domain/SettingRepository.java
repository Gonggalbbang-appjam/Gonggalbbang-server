package com.example.gonggalbbang.domain.user.domain;

import com.example.gonggalbbang.domain.auth.domain.entity.User;
import com.example.gonggalbbang.domain.user.domain.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettingRepository extends JpaRepository<Setting, Long> {
    Optional<Setting> findByUser(User user);
}
