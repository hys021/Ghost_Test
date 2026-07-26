package com.example.test.history.repository;

import com.example.test.history.entity.TestHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface TestHistoryRepository extends JpaRepository<TestHistory, Long> {
    @Query("""
            SELECT t.ghostId, COUNT(t)
            FROM TestHistory t
            GROUP BY t.ghostId
            ORDER BY COUNT(t) DESC
            """)
    List<Object[]> findGhostRanking();

    @Query("""
        SELECT t.ghostId, COUNT(t)
        FROM TestHistory t
        WHERE t.schoolId = :schoolId
        GROUP BY t.ghostId
        ORDER BY COUNT(t) DESC
        """)
    List<Object[]> findSchoolRanking(Long schoolId);

    long countBySchoolId(Long schoolId);

    @Query("""
    SELECT t.schoolId, COUNT(t)
    FROM TestHistory t
    WHERE t.schoolId IS NOT NULL
    GROUP BY t.schoolId
    ORDER BY COUNT(t) DESC
    """)
    List<Object[]> findSchoolParticipantRanking();
}