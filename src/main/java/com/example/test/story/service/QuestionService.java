package com.example.test.story.service;

import com.example.test.story.dto.ChoiceResponse;
import com.example.test.story.dto.QuestionListResponse;
import com.example.test.story.dto.QuestionResponse;
import com.example.test.story.entity.Choice;
import com.example.test.story.entity.Question;
import com.example.test.story.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    /**
     * sequence 순서대로 전체 질문 + 선택지를 조회해서 응답 DTO로 변환한다.
     */
    public QuestionListResponse getAllQuestions() {

        List<Question> questions = questionRepository.findAllByOrderBySequenceAsc();

        List<QuestionResponse> questionResponses = questions.stream()
                .map(this::toQuestionResponse)
                .toList();

        return new QuestionListResponse(questionResponses);
    }

    private QuestionResponse toQuestionResponse(Question question) {

        List<ChoiceResponse> choiceResponses = question.getChoices().stream()
                .sorted(Comparator.comparing(Choice::getChoiceOrder))
                .map(this::toChoiceResponse)
                .toList();

        return new QuestionResponse(
                question.getId(),
                question.getContent(),
                question.getImageUrl(),
                choiceResponses
        );
    }

    private ChoiceResponse toChoiceResponse(Choice choice) {
        return new ChoiceResponse(
                choice.getId(),
                choice.getChoiceOrder(),
                choice.getContent(),
                choice.getReactionText()
        );
    }
}