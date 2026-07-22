package com.example.test.history.service;

import com.example.test.history.dto.HistorySaveRequest;
import com.example.test.history.dto.HistoryResponse;
import com.example.test.history.dto.MatchInfo;
import com.example.test.history.dto.ParticipantCountResponse;
import com.example.test.history.entity.TestHistory;
import com.example.test.history.repository.TestHistoryRepository;

// 확정된 Ghost 엔티티 및 레포지토리 패키지 연결
import com.example.test.ghost.entity.Ghost;
import com.example.test.ghost.repository.GhostRepository;

// 하헌승 님의 School 엔티티 및 레포지토리 연결
import com.example.test.school.entity.School;
import com.example.test.school.repository.SchoolRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistoryService {

    private final TestHistoryRepository testHistoryRepository;
    private final GhostRepository ghostRepository;
    private final SchoolRepository schoolRepository;

    // TODO: 윤석 님의 MBTI 계산 서비스 합류 시 주석 해제 후 주입
    // private final GhostCalculatorService ghostCalculatorService;

    /**
     * 1. 테스트 최종 결과 계산 및 저장 (POST /api/v1/histories)
     */
    @Transactional
    public HistoryResponse saveHistory(HistorySaveRequest request) {
        // TODO: 윤석 님의 계산 로직 구현 시 아래 코드로 교체 예정
        // String mbtiType = ghostCalculatorService.calculateMbti(request.getChoiceIds());
        String mbtiType = "INFP"; // 임시 기본값

        // 도출된 MBTI 타입으로 실제 도감 데이터 매핑
        Ghost ghost = ghostRepository.findByMbtiType(mbtiType)
                .orElseThrow(() -> new IllegalArgumentException("해당 MBTI 유형의 귀신 데이터가 도감에 존재하지 않습니다: " + mbtiType));

        // 테스트 이력 엔티티 빌드 및 실제 MySQL 적재 (0번 또는 null은 선택 안 함으로 안전하게 치환됨)
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
                .orElseThrow(() -> new IllegalArgumentException("해당 테스트 이력이 존재하지 않습니다. id=" + id));

        return convertToHistoryResponse(testHistory);
    }

    /**
     * DB 식별자 조인을 통해 실 데이터를 바인딩하는 컨버터
     */
    private HistoryResponse convertToHistoryResponse(TestHistory history) {

        // 1. School 정보 동적 조회 및 0번/null 예외 처리 (수정 완료)
        String schoolName = "선택 안 함";
        if (history.getSchoolId() != null && history.getSchoolId() != 0) {
            schoolName = schoolRepository.findById(history.getSchoolId())
                    .map(School::getName)
                    .orElse("알 수 없는 학교");
        }

        // 2. 이력에 적재된 ghostId 기반으로 결과 귀신 실시간 매핑
        Ghost ghost = ghostRepository.findById(history.getGhostId())
                .orElseThrow(() -> new IllegalArgumentException("귀신 도감 정보가 실존하지 않습니다. id=" + history.getGhostId()));

        // 3. 찰떡 궁합(Best Match) 귀신 상세 정보 추적
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

        // 4. 파멸 궁합(Worst Match) 귀신 상세 정보 추적
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

        // 5. 확정된 실 데이터 기반 DTO 바인딩
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