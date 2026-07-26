package com.example.test.school.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SchoolParticipantRankingListResponse {

    private long participantCount;
    private List<SchoolParticipantRankingResponse> rankings;
}