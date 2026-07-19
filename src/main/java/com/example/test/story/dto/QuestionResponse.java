package com.example.test.story.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class QuestionResponse {

    private Long questionId;
    private String content;
    private String imageUrl;
    private List<ChoiceResponse> choices;
}