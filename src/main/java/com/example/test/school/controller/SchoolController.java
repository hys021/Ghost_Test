package com.example.test.school.controller;

import com.example.test.school.dto.SchoolListResponse;
import com.example.test.school.service.SchoolService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.test.ranking.dto.RankingListResponse;
import com.example.test.ranking.service.RankingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.test.ranking.dto.SchoolRankingResponse;

@RestController
@RequestMapping("/api/v1")
public class SchoolController {

    private final SchoolService schoolService;
    private final RankingService rankingService;

    public SchoolController(SchoolService schoolService,
                            RankingService rankingService) {
        this.schoolService = schoolService;
        this.rankingService = rankingService;
    }

    @GetMapping("/schools")
    public SchoolListResponse searchSchools(@RequestParam String keyword) {
        return schoolService.searchSchools(keyword);
    }

    @GetMapping("/schools/{schoolId}/rankings")
    public ResponseEntity<SchoolRankingResponse> getSchoolRanking(
            @PathVariable Long schoolId) {

        return ResponseEntity.ok(rankingService.getSchoolRanking(schoolId));
    }
}