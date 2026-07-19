package com.example.test.repository;

import com.example.test.entity.TestHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestHistoryRepository extends JpaRepository<TestHistory, Long> {
    // Spring Data JPA가 save(), findById() 등의 기본 CRUD 메서드를 자동으로 제공합니다.
}