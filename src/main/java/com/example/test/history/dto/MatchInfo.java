package com.example.test.history.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MatchInfo {
    private String ghostName;
    private String comment;
}