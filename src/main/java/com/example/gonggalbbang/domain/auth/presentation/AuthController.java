package com.example.gonggalbbang.domain.auth.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.gonggalbbang.domain.auth.presentation.dto.request.JoinReq;
import com.example.gonggalbbang.domain.auth.presentation.dto.request.LoginReq;
import com.example.gonggalbbang.domain.auth.presentation.dto.request.RefreshReq;
import com.example.gonggalbbang.domain.auth.presentation.dto.response.LoginRes;
import com.example.gonggalbbang.domain.auth.presentation.dto.response.RefreshRes;
import com.example.gonggalbbang.domain.auth.service.AuthService;
import com.example.gonggalbbang.global.common.Response;
import com.example.gonggalbbang.global.common.ResponseData;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "auth", description = "auth API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "회원가입", description = "회원가입합니다. (unauthenticated)")
    public Response join(@RequestBody JoinReq joinReq) {
        return authService.join(joinReq);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인 합니다. (unauthenticated)")
    public ResponseData<LoginRes> login(@Validated @RequestBody LoginReq loginReq) {
        return authService.login(loginReq);
    }

    @PostMapping("/refresh")
    @Operation(summary = "토큰 재발급", description = "access 토큰 재발급")
    public ResponseData<RefreshRes> refresh(@Validated @RequestBody RefreshReq refreshReq) {
        return authService.refresh(refreshReq);
    }
}
