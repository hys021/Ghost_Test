package com.example.test.history.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class HistoryResponse {
    private Long id;
    private String schoolName;
    private String ghostType;
    private String ghostName;
    private String description;
    private String imageUrl;
    private String aiStory;
    private MatchInfo bestMatch;
    private MatchInfo worstMatch;
    private LocalDateTime createdAt;
}