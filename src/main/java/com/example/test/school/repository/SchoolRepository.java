package com.example.test.school.repository;

import com.example.test.school.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolRepository extends JpaRepository<School, Long> {

    List<School> findByNameContaining(String keyword);

}