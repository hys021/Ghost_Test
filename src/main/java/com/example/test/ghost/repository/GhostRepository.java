package com.example.test.ghost.repository;

import com.example.test.ghost.entity.Ghost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GhostRepository extends JpaRepository<Ghost, Long> {

    Optional<Ghost> findByMbtiType(String mbtiType);
}