package com.example.test.school.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SchoolParticipantRankingResponse {

    private int rank;
    private Long schoolId;
    private String schoolName;
    private long count;
    private double percent;
}