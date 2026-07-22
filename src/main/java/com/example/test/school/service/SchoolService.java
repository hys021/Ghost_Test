package com.example.test.school.service;

import com.example.test.school.dto.SchoolListResponse;
import com.example.test.school.dto.SchoolResponse;
import com.example.test.school.entity.School;
import com.example.test.school.repository.SchoolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public SchoolListResponse searchSchools(String keyword) {

        List<SchoolResponse> schools = schoolRepository.findByNameContaining(keyword)
                .stream()
                .map(school -> new SchoolResponse(
                        school.getId(),
                        school.getName()
                ))
                .toList();

        return new SchoolListResponse(schools);
    }
}