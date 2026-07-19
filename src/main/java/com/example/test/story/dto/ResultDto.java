package com.example.test.story.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResultDto {

    private String mbti;       // 계산된 MBTI (ex: "ENTP")
    private Long ghostId;      // 매칭된 귀신의 PK
    private String ghostType;  // 매칭된 귀신의 코드값 (ex: "NINE_TAILED_FOX")
}