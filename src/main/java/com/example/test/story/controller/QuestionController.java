package com.example.test.story.controller;

import com.example.test.story.dto.QuestionListResponse;
import com.example.test.story.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/questions")
    public QuestionListResponse getQuestions() {
        return questionService.getAllQuestions();
    }
}