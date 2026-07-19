package com.example.test.history.controller;

import com.example.test.history.dto.HistorySaveRequest;
import com.example.test.history.dto.HistoryResponse;
import com.example.test.history.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/histories")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    /**
     * 1. 테스트 최종 결과 계산 및 저장
     * Method / URL: POST /api/v1/histories
     */
    @PostMapping
    public ResponseEntity<HistoryResponse> createHistory(@RequestBody HistorySaveRequest request) {
        HistoryResponse response = historyService.saveHistory(request);
        // 명세서 상의 201 Created 응답 코드를 반환하기 위해 ResponseEntity.status()를 사용합니다.
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 2. 테스트 결과 상세 조회 (결과창 및 공유 링크 공용)
     * Method / URL: GET /api/v1/histories/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<HistoryResponse> getHistoryDetail(@PathVariable("id") Long id) {
        HistoryResponse response = historyService.getHistoryDetail(id);
        // 명세서 상의 200 OK 응답 코드로 데이터를 반환합니다.
        return ResponseEntity.ok(response);
    }
}