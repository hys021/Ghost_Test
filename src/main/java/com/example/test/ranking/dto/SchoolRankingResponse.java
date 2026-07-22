package com.example.test.ranking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SchoolRankingResponse {

    private String schoolName;
    private long participantCount;
    private List<RankingResponse> rankings;
}
