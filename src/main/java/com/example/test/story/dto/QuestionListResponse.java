package com.example.test.story.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class QuestionListResponse {

    private List<QuestionResponse> questions;
}