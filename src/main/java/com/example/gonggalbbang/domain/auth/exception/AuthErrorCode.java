package com.example.gonggalbbang.domain.auth.exception;

import com.example.gonggalbbang.global.exception.error.CustomErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements CustomErrorCode {

    USER_ALREADY(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "아이디를 찾을 수 없습니다."),
    PASSWORD_WRONG(HttpStatus.BAD_REQUEST, "비밀번호가 맞지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
