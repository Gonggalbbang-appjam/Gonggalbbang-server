package com.example.gonggalbbang.global.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @Getter
    @Builder
    @RequiredArgsConstructor
    private static class ErrorResponse {
        private final int status;
        private final String message;
    }

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<ErrorResponse> handleException(CustomException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .status(ex.getError().getStatus().value())
                .message(ex.getError().getMessage())
                .build();
        return new ResponseEntity<ErrorResponse>(response, ex.getError().getStatus());
    }
}
