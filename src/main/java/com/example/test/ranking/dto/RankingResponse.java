package com.example.test.ranking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RankingResponse {

    private int rank;
    private String ghostName;
    private String description;
    private long count;
    private double percent;
}