package com.example.test.history.service;

import com.example.test.history.dto.HistorySaveRequest;
import com.example.test.history.dto.HistoryResponse;
import com.example.test.history.dto.MatchInfo;
import com.example.test.history.entity.TestHistory;
import com.example.test.history.repository.TestHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import com.example.test.history.dto.ParticipantCountResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistoryService {

    private final TestHistoryRepository testHistoryRepository;

    @Transactional
    public HistoryResponse saveHistory(HistorySaveRequest request) {
        // 1. 윤석님 구현 영역 연동 공간 (임시 ID 발급)
        Long calculatedGhostId = 1L;

        // 2. 이력 엔티티 생성 및 DB 저장
        TestHistory testHistory = TestHistory.builder()
                .schoolId(request.getSchoolId())
                .ghostId(calculatedGhostId)
                .build();

        TestHistory savedHistory = testHistoryRepository.save(testHistory);

        return convertToHistoryResponse(savedHistory);
    }

    public HistoryResponse getHistoryDetail(Long id) {
        TestHistory testHistory = testHistoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 테스트 이력이 존재하지 않습니다. id=" + id));

        return convertToHistoryResponse(testHistory);
    }

    private HistoryResponse convertToHistoryResponse(TestHistory history) {
        return HistoryResponse.builder()
                .id(history.getId())
                .schoolName("인하고등학교")
                .ghostType("WATER_GHOST")
                .ghostName("물귀신")
                .description("한 줄 설명이 들어가는 공간입니다.")
                .imageUrl("https://.../water_ghost.png")
                .aiStory("밤마다 학교 분수대 주변을 떠도는 이 귀신은...")
                .bestMatch(MatchInfo.builder()
                        .ghostName("설녀")
                        .comment("말 한마디로 사람 얼려버리는 설녀")
                        .build())
                .worstMatch(MatchInfo.builder()
                        .ghostName("홍길동")
                        .comment("맨날 나타난다고 말만 하고 안 나타나는 홍길동")
                        .build())
                .createdAt(history.getCreatedAt() != null ? history.getCreatedAt() : LocalDateTime.now())
                .build();
    }

    public ParticipantCountResponse getParticipantCount() {
        long count = testHistoryRepository.count();
        return new ParticipantCountResponse(count);
    }
}