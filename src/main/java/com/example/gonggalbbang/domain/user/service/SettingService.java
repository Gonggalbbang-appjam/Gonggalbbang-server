package com.example.gonggalbbang.domain.user.service;

import com.example.gonggalbbang.domain.auth.domain.AuthRepository;
import com.example.gonggalbbang.domain.auth.domain.entity.User;
import com.example.gonggalbbang.domain.auth.exception.AuthErrorCode;
import com.example.gonggalbbang.domain.user.domain.SettingRepository;
import com.example.gonggalbbang.domain.user.domain.entity.Setting;
import com.example.gonggalbbang.domain.user.presentation.dto.request.SettingReq;
import com.example.gonggalbbang.global.common.Response;
import com.example.gonggalbbang.global.common.ResponseData;
import com.example.gonggalbbang.global.exception.CustomException;
import com.example.gonggalbbang.global.security.auth.AuthDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class SettingService {

    private final SettingRepository settingRepository;
    private final AuthRepository authRepository;

    public Response createSetting(SettingReq settingReq) {
        String username = getCurrentUsername();
        User user = authRepository.findById(username)
                .orElseThrow(() -> new CustomException(AuthErrorCode.USER_NOT_FOUND));

        Setting setting = settingRepository.findByUser(user)
                .map(existingSetting -> updateSetting(existingSetting, settingReq))
                .orElseGet(() -> Setting.fromSettingReq(settingReq, user));

        settingRepository.save(setting);
        return Response.created("설정 저장 성공");
    }

    @Transactional(readOnly = true)
    public ResponseData<Setting> getMySetting() {
        User user = getCurrentUser();

        Setting setting = settingRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(AuthErrorCode.USER_NOT_FOUND));

        return ResponseData.ok("설정 조회 성공", setting);
    }

    private User getCurrentUser() {
        String username = getCurrentUsername();
        return authRepository.findById(username)
                .orElseThrow(() -> new CustomException(AuthErrorCode.USER_NOT_FOUND));
    }

    private String getCurrentUsername() {
        AuthDetails authDetails = (AuthDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return authDetails.getUsername();
    }

    private Setting updateSetting(Setting setting, SettingReq settingReq) {
        return Setting.builder()
                .id(setting.getId()) // 기존 ID 유지
                .upType(settingReq.upType())
                .favorite(settingReq.favorite())
                .payment(settingReq.payment())
                .user(setting.getUser()) // 기존 유저 유지
                .build();
    }


}
