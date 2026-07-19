package com.example.test.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MatchInfo {
    private Long schoolId;
    private Long ghostId;
}