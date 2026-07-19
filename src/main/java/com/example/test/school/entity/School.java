package com.example.test.school.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "school")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "school_type", nullable = false)
    private SchoolType schoolType;

    @Column(nullable = false, length = 30)
    private String region;

    @Column(nullable = false, length = 50)
    private String district;

    protected School() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public SchoolType getSchoolType() {
        return schoolType;
    }

    public String getRegion() {
        return region;
    }

    public String getDistrict() {
        return district;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSchoolType(SchoolType schoolType) {
        this.schoolType = schoolType;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}