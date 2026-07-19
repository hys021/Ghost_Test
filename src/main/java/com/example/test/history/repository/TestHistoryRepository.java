package com.example.test.history.repository;

import com.example.test.history.entity.TestHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestHistoryRepository extends JpaRepository<TestHistory, Long> {
}