package com.example.test.ranking.service;

import com.example.test.ghost.entity.Ghost;
import com.example.test.ghost.repository.GhostRepository;
import com.example.test.history.repository.TestHistoryRepository;
import com.example.test.ranking.dto.RankingListResponse;
import com.example.test.ranking.dto.RankingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RankingService {

    private final TestHistoryRepository testHistoryRepository;
    private final GhostRepository ghostRepository;

    public RankingListResponse getRanking() {

        long participantCount = testHistoryRepository.count();

        List<Object[]> rankingData = testHistoryRepository.findGhostRanking();
        List<RankingResponse> rankings = new ArrayList<>();

        int rank = 1;

        for (Object[] row : rankingData) {

            Long ghostId = (Long) row[0];
            Long count = (Long) row[1];

            Ghost ghost = ghostRepository.findById(ghostId)
                    .orElseThrow(() -> new IllegalArgumentException("귀신을 찾을 수 없습니다."));

            double percent = participantCount == 0
                    ? 0
                    : (count * 100.0 / participantCount);

            rankings.add(new RankingResponse(
                    rank++,
                    ghost.getGhostType(),
                    count,
                    Math.round(percent * 10) / 10.0
            ));
        }

        return new RankingListResponse(participantCount, rankings);
    }

    public RankingListResponse getSchoolRanking(Long schoolId) {

        long participantCount = testHistoryRepository.countBySchoolId(schoolId);

        List<Object[]> rankingData = testHistoryRepository.findSchoolRanking(schoolId);
        List<RankingResponse> rankings = new ArrayList<>();

        int rank = 1;

        for (Object[] row : rankingData) {

            Long ghostId = ((Number) row[0]).longValue();
            long count = ((Number) row[1]).longValue();

            Ghost ghost = ghostRepository.findById(ghostId)
                    .orElseThrow(() -> new IllegalArgumentException("귀신을 찾을 수 없습니다."));

            double percent = participantCount == 0
                    ? 0
                    : count * 100.0 / participantCount;

            rankings.add(new RankingResponse(
                    rank++,
                    ghost.getGhostType(),
                    count,
                    Math.round(percent * 10) / 10.0
            ));
        }

        return new RankingListResponse(participantCount, rankings);
    }
}