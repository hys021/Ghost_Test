package com.example.test.school.dto;

public class SchoolResponse {

    private Long schoolId;
    private String schoolName;

    public SchoolResponse(Long schoolId, String schoolName) {
        this.schoolId = schoolId;
        this.schoolName = schoolName;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }
}