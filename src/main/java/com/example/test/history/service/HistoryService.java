package com.example.test.history.service;

import com.example.test.history.dto.HistorySaveRequest;
import com.example.test.history.dto.HistoryResponse;
import com.example.test.history.dto.MatchInfo;
import com.example.test.history.dto.ParticipantCountResponse;
import com.example.test.history.entity.TestHistory;
import com.example.test.history.repository.TestHistoryRepository;

// Ghost 도메인 연동
import com.example.test.ghost.entity.Ghost;
import com.example.test.ghost.repository.GhostRepository;

// School 도메인 연동
import com.example.test.school.entity.School;
import com.example.test.school.repository.SchoolRepository;

// Story 도메인 연동
import com.example.test.story.service.ResultCalculationService;
import com.example.test.story.dto.ResultDto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistoryService {

    private final TestHistoryRepository testHistoryRepository;
    private final GhostRepository ghostRepository;
    private final SchoolRepository schoolRepository;

    private final ResultCalculationService resultCalculationService;

    /**
     * 1. 테스트 최종 결과 계산 및 저장 (POST /api/v1/histories)
     */
    @Transactional
    public HistoryResponse saveHistory(HistorySaveRequest request) {

        ResultDto result = resultCalculationService.calculate(request.getChoiceIds());
        String calculatedMbti = result.getMbti();

        Ghost ghost = ghostRepository.findByMbtiType(calculatedMbti)
                .orElseThrow(() -> new IllegalArgumentException("해당 MBTI 유형의 귀신 데이터가 도감에 존재하지 않습니다: " + calculatedMbti));

        TestHistory testHistory = TestHistory.builder()
                .schoolId(request.getSchoolId())
                .ghostId(ghost.getId())
                .build();

        TestHistory savedHistory = testHistoryRepository.save(testHistory);

        return convertToHistoryResponse(savedHistory);
    }

    /**
     * 2. 테스트 결과 상세 조회 (GET /api/v1/histories/{id})
     */
    public HistoryResponse getHistoryDetail(Long id) {
        TestHistory testHistory = testHistoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "해당 테스트 이력이 존재하지 않습니다. id=" + id
                ));

        return convertToHistoryResponse(testHistory);
    }

    /**
     * DB 식별자 조인을 통해 실 데이터를 바인딩하는 컨버터
     */
    private HistoryResponse convertToHistoryResponse(TestHistory history) {

        // 1. School 정보 동적 조회 및 0번/Null 예외 방어
        String schoolName = "선택 안 함";
        if (history.getSchoolId() != null) {
            schoolName = schoolRepository.findById(history.getSchoolId())
                    .map(School::getName)
                    .orElse("알 수 없는 학교");
        }

        Ghost ghost = ghostRepository.findById(history.getGhostId())
                .orElseThrow(() -> new IllegalArgumentException("귀신 도감 정보가 실존하지 않습니다. id=" + history.getGhostId()));

        MatchInfo bestMatchInfo = null;
        if (ghost.getBestMatchGhostId() != null) {
            Ghost bestGhost = ghostRepository.findById(ghost.getBestMatchGhostId()).orElse(null);
            if (bestGhost != null) {
                bestMatchInfo = MatchInfo.builder()
                        .ghostName(bestGhost.getName())
                        .comment(ghost.getBestMatchComment())
                        .build();
            }
        }

        MatchInfo worstMatchInfo = null;
        if (ghost.getWorstMatchGhostId() != null) {
            Ghost worstGhost = ghostRepository.findById(ghost.getWorstMatchGhostId()).orElse(null);
            if (worstGhost != null) {
                worstMatchInfo = MatchInfo.builder()
                        .ghostName(worstGhost.getName())
                        .comment(ghost.getWorstMatchComment())
                        .build();
            }
        }

        return HistoryResponse.builder()
                .id(history.getId())
                .schoolName(schoolName)
                .ghostType(ghost.getGhostType())
                .ghostName(ghost.getName())
                .description(ghost.getDescription())
                .imageUrl(ghost.getImageUrl())
                .aiStory("밤마다 " + schoolName + " 주변을 떠도는 " + ghost.getName() + "의 비밀 이야기...")
                .bestMatch(bestMatchInfo)
                .worstMatch(worstMatchInfo)
                .createdAt(history.getCreatedAt() != null ? history.getCreatedAt() : LocalDateTime.now())
                .build();
    }

    /**
     * 3. 전체 참여자 수 조회 (GET /api/v1/histories/participants/count)
     */
    public ParticipantCountResponse getParticipantCount() {
        long count = testHistoryRepository.count();
        return new ParticipantCountResponse(count);
    }
}