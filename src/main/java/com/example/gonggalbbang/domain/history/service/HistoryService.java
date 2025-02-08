package com.example.gonggalbbang.domain.history.service;

import com.example.gonggalbbang.domain.auth.domain.AuthRepository;
import com.example.gonggalbbang.domain.auth.domain.entity.User;
import com.example.gonggalbbang.domain.auth.exception.AuthErrorCode;
import com.example.gonggalbbang.domain.history.domain.HistoryRepository;
import com.example.gonggalbbang.domain.history.presentation.dto.response.HistoryRes;
import com.example.gonggalbbang.global.common.ResponseData;
import com.example.gonggalbbang.global.exception.CustomException;
import com.example.gonggalbbang.global.security.auth.AuthDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final AuthRepository authRepository;

    public ResponseData<List<HistoryRes>> getHistories() {
        User currentUser = getCurrentUser();
        
        List<HistoryRes> histories = historyRepository.findByUserOrderByDonationDateDesc(currentUser)
                .stream()
                .map(HistoryRes::from)
                .collect(Collectors.toList());
                
        return ResponseData.ok("기부 내역 조회 성공", histories);
    }

    private User getCurrentUser() {
        AuthDetails authDetails = (AuthDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return authRepository.findById(authDetails.getUsername())
                .orElseThrow(() -> new CustomException(AuthErrorCode.USER_NOT_FOUND));
    }
} 