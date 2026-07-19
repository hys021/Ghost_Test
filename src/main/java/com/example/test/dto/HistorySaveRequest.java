package com.example.test.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HistorySaveRequest {
    private Long schoolId;
    private Long ghostId;
}