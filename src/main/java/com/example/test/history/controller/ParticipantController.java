package com.example.test.history.controller;

import com.example.test.history.dto.ParticipantCountResponse;
import com.example.test.history.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ParticipantController {

    private final HistoryService historyService;

    @GetMapping("/participant-count")
    public ResponseEntity<ParticipantCountResponse> getParticipantCount() {
        return ResponseEntity.ok(historyService.getParticipantCount());
    }
}