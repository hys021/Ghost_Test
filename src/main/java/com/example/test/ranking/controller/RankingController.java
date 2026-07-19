package com.example.test.ranking.controller;

import com.example.test.ranking.dto.RankingListResponse;
import com.example.test.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rankings")
@RequiredArgsConstructor
public class RankingController {

    private final RankingService rankingService;

    @GetMapping
    public ResponseEntity<RankingListResponse> getRanking() {
        return ResponseEntity.ok(rankingService.getRanking());
    }
}