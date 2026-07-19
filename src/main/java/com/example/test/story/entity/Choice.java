package com.example.test.story.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "choice")
@Getter
@Setter
@NoArgsConstructor
public class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "content", nullable = false, length = 100)
    private String content;

    @Column(name = "reaction_text", length = 255)
    private String reactionText;

    @Column(name = "choice_order", nullable = false)
    private Integer choiceOrder;

    @Column(name = "ei_score", nullable = false)
    private Integer eiScore = 0;

    @Column(name = "ns_score", nullable = false)
    private Integer nsScore = 0;

    @Column(name = "tf_score", nullable = false)
    private Integer tfScore = 0;

    @Column(name = "jp_score", nullable = false)
    private Integer jpScore = 0;
}