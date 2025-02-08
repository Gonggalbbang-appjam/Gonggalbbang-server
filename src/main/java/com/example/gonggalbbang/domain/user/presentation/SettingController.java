package com.example.gonggalbbang.domain.user.presentation;

import com.example.gonggalbbang.domain.user.domain.entity.Setting;
import com.example.gonggalbbang.domain.user.presentation.dto.request.SettingReq;
import com.example.gonggalbbang.domain.user.service.SettingService;
import com.example.gonggalbbang.global.common.Response;
import com.example.gonggalbbang.global.common.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/settings")
@Tag(name = "settings", description = "settings API")
@RequiredArgsConstructor
public class SettingController {

    private final SettingService settingService;

    @GetMapping("/me")
    @Operation(summary = "내 설정 보기", description = "사용자의 설정을 가져옵니다.")
    public ResponseData<Setting> getMySetting() {
        return settingService.getMySetting();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "설정 저장", description = "사용자의 설정을 저장합니다. upType은 HUNDRED, THOUSAND 둘 중에 하나")
    public Response createSetting(@Validated @RequestBody SettingReq settingReq) {
        return settingService.createSetting(settingReq);
    }
}
