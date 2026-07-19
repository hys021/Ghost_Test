package com.example.test.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class HistoryDetailResponse {
    private int totalCount;
    private List<MatchInfo> histories;
}