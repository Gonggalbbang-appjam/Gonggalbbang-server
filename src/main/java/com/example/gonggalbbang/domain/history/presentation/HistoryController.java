package com.example.gonggalbbang.domain.history.presentation;

import com.example.gonggalbbang.domain.history.presentation.dto.response.HistoryRes;
import com.example.gonggalbbang.domain.history.service.HistoryService;
import com.example.gonggalbbang.global.common.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/histories")
@Tag(name = "histories", description = "donation histories API")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping
    @Operation(summary = "기부 내역 조회", description = "전체 기부 내역을 조회합니다.")
    public ResponseData<List<HistoryRes>> getHistories() {
        return historyService.getHistories();
    }
} 