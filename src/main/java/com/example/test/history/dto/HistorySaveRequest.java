package com.example.test.history.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
public class HistorySaveRequest {
    private Long schoolId;
    private List<Long> choiceIds;
}