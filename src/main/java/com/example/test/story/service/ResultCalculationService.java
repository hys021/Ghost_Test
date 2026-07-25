package com.example.test.story.service;

import com.example.test.ghost.entity.Ghost;
import com.example.test.ghost.repository.GhostRepository;
import com.example.test.story.dto.ResultDto;
import com.example.test.story.entity.Choice;
import com.example.test.story.repository.ChoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.test.story.repository.QuestionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultCalculationService {

    private final ChoiceRepository choiceRepository;
    private final GhostRepository ghostRepository;
    private final QuestionRepository questionRepository;

    /**
     * 유저가 선택한 choiceId 리스트를 받아 MBTI를 계산하고,
     * 매칭되는 귀신 정보를 반환.
     *
     * @param choiceIds 유저가 각 질문에서 고른 선택지 id 리스트
     * @return 계산된 MBTI + 매칭된 귀신 정보
     */
    public ResultDto calculate(List<Long> choiceIds) {

        if (choiceIds == null || choiceIds.isEmpty()) {
            throw new IllegalArgumentException("선택한 답변이 없습니다.");
        }

        long questionCount = questionRepository.findAllByOrderBySequenceAsc().size();

        if (choiceIds.size() != questionCount) {
            throw new IllegalArgumentException("모든 질문에 대한 답변이 필요합니다.");
        }

        int ei = 0, ns = 0, tf = 0, jp = 0;

        List<Choice> choices = choiceRepository.findAllById(choiceIds);

        if (choices.size() != choiceIds.size()) {
            throw new IllegalArgumentException("존재하지 않는 선택지가 포함되어 있습니다.");
        }

        for (Choice choice : choices) {
            ei += choice.getEiScore();
            ns += choice.getNsScore();
            tf += choice.getTfScore();
            jp += choice.getJpScore();
        }

        // 2. 합산 점수로 MBTI 4글자 도출
        //    동점(0)일 경우 규칙: E/N/T/J 쪽으로 처리
        String mbti = ""
                + (ei >= 0 ? "E" : "I")
                + (ns >= 0 ? "N" : "S")
                + (tf >= 0 ? "T" : "F")
                + (jp >= 0 ? "J" : "P");

        // 3. MBTI로 귀신 조회
        Ghost ghost = ghostRepository.findByMbtiType(mbti)
                .orElseThrow(() -> new IllegalStateException("해당 MBTI에 매칭되는 귀신이 없습니다: " + mbti));

        return new ResultDto(mbti, ghost.getId(), ghost.getGhostType());
    }
}