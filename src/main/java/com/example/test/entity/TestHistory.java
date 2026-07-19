package com.example.test.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "test_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TestHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "school_id")
    private Long schoolId;

    @Column(name = "ghost_id", nullable = false)
    private Long ghostId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public TestHistory(Long schoolId, Long ghostId) {
        this.schoolId = schoolId;
        this.ghostId = ghostId;
    }
}