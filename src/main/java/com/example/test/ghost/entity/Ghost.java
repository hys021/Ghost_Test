package com.example.test.ghost.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ghost")
@Getter
@Setter
@NoArgsConstructor
public class Ghost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mbti_type", nullable = false, unique = true, length = 4)
    private String mbtiType;

    @Column(name = "ghost_type", nullable = false, unique = true, length = 50)
    private String ghostType;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(name = "best_match_ghost_id")
    private Long bestMatchGhostId;

    @Column(name = "best_match_comment", length = 255)
    private String bestMatchComment;

    @Column(name = "worst_match_ghost_id")
    private Long worstMatchGhostId;

    @Column(name = "worst_match_comment", length = 255)
    private String worstMatchComment;

    @Column(name = "ai_story", columnDefinition = "TEXT")
    private String aiStory;
}