package com.careerbridge.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "profiles",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id"})
    }
)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    private String phone;

    private String location;

    private String careerGoal;

    @Column(length = 2000)
    private String bio;

    @Column(length = 2000)
    private String skills;

    private String githubUrl;

    private String linkedinUrl;

    // Education
    private String degree;

    private String college;

    private String branch;

    private String graduationYear;

    private String cgpa;


    public Profile() {
    }


    public Profile(
            Long userId,
            String phone,
            String location,
            String careerGoal,
            String bio,
            String skills,
            String githubUrl,
            String linkedinUrl,
            String degree,
            String college,
            String branch,
            String graduationYear,
            String cgpa
    ) {
        this.userId = userId;
        this.phone = phone;
        this.location = location;
        this.careerGoal = careerGoal;
        this.bio = bio;
        this.skills = skills;
        this.githubUrl = githubUrl;
        this.linkedinUrl = linkedinUrl;
        this.degree = degree;
        this.college = college;
        this.branch = branch;
        this.graduationYear = graduationYear;
        this.cgpa = cgpa;
    }


    public Long getId() {
        return id;
    }


    public Long getUserId() {
        return userId;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public String getPhone() {
        return phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getLocation() {
        return location;
    }


    public void setLocation(String location) {
        this.location = location;
    }


    public String getCareerGoal() {
        return careerGoal;
    }


    public void setCareerGoal(String careerGoal) {
        this.careerGoal = careerGoal;
    }


    public String getBio() {
        return bio;
    }


    public void setBio(String bio) {
        this.bio = bio;
    }


    public String getSkills() {
        return skills;
    }


    public void setSkills(String skills) {
        this.skills = skills;
    }


    public String getGithubUrl() {
        return githubUrl;
    }


    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }


    public String getLinkedinUrl() {
        return linkedinUrl;
    }


    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }


    // Education getters and setters

    public String getDegree() {
        return degree;
    }


    public void setDegree(String degree) {
        this.degree = degree;
    }


    public String getCollege() {
        return college;
    }


    public void setCollege(String college) {
        this.college = college;
    }


    public String getBranch() {
        return branch;
    }


    public void setBranch(String branch) {
        this.branch = branch;
    }


    public String getGraduationYear() {
        return graduationYear;
    }


    public void setGraduationYear(String graduationYear) {
        this.graduationYear = graduationYear;
    }


    public String getCgpa() {
        return cgpa;
    }


    public void setCgpa(String cgpa) {
        this.cgpa = cgpa;
    }
}