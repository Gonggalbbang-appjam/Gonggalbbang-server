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

    // 설정을 생성하는 메서드
    public Response createSetting(SettingReq settingReq) {
        String username = getCurrentUsername();
        User user = authRepository.findById(username)
                .orElseThrow(() -> new CustomException(AuthErrorCode.USER_NOT_FOUND));

        // 사용자가 이미 설정을 가지고 있으면 기존 설정을 갱신해야 하는지, 아니면 새 설정을 추가해야 하는지 결정
        Setting setting = settingRepository.findByUser(user)
                .map(existingSetting -> {
                    // 이미 설정이 존재하는 경우에는 업데이트
                    return updateSetting(existingSetting, settingReq);
                })
                .orElseGet(() -> {
                    // 기존 설정이 없으면 새로운 설정을 생성
                    return Setting.fromSettingReq(settingReq, user);
                });

        // 최종적으로 설정 저장
        settingRepository.save(setting);
        return Response.created("설정 저장 성공");
    }

    // 내 설정을 조회하는 메서드
    @Transactional(readOnly = true)
    public ResponseData<Setting> getMySetting() {
        User user = getCurrentUser();

        Setting setting = settingRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(AuthErrorCode.USER_NOT_FOUND));

        return ResponseData.ok("설정 조회 성공", setting);
    }

    // 설정을 수정하는 메서드
    public Response updateSetting(SettingReq settingReq) {
        User user = getCurrentUser();
        Setting setting = settingRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(AuthErrorCode.USER_NOT_FOUND));

        // 기존 설정 수정
        setting = updateSetting(setting, settingReq);
        settingRepository.save(setting);  // 변경 사항 저장

        return Response.ok("설정 수정 성공");
    }

    // 현재 사용자를 가져오는 메서드
    private User getCurrentUser() {
        String username = getCurrentUsername();
        return authRepository.findById(username)
                .orElseThrow(() -> new CustomException(AuthErrorCode.USER_NOT_FOUND));
    }

    // 현재 로그인한 사용자의 username을 가져오는 메서드
    private String getCurrentUsername() {
        AuthDetails authDetails = (AuthDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return authDetails.getUsername();
    }

    // Setting 객체를 업데이트하는 메서드
    private Setting updateSetting(Setting setting, SettingReq settingReq) {
        return Setting.builder()
                .id(setting.getId())  // 기존 ID 유지
                .upType(settingReq.upType())
                .favorite(settingReq.favorite())
                .payment(settingReq.payment())
                .user(setting.getUser())  // 기존 유저 유지
                .build();
    }


}
