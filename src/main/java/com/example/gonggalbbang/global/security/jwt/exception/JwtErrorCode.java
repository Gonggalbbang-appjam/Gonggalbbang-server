package com.example.gonggalbbang.global.security.jwt.exception;

import com.example.gonggalbbang.global.exception.error.CustomErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum JwtErrorCode implements CustomErrorCode {

    TOKEN_TYPE_ERROR(HttpStatus.BAD_REQUEST,"잘못된 타입");

    private final HttpStatus status;
    private final String message;

}
