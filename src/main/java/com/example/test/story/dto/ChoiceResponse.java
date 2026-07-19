package com.example.test.story.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChoiceResponse {

    private Long choiceId;
    private Integer choiceOrder;
    private String content;
    private String reactionText;
}