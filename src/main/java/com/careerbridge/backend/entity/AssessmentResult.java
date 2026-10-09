package com.careerbridge.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "assessment_results")
public class AssessmentResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private int aiMlScore;

    private int dataScientistScore;

    private int softwareEngineerScore;

    private LocalDateTime completedAt;

    public AssessmentResult() {
    }

    public AssessmentResult(
            Long userId,
            int aiMlScore,
            int dataScientistScore,
            int softwareEngineerScore
    ) {
        this.userId = userId;
        this.aiMlScore = aiMlScore;
        this.dataScientistScore = dataScientistScore;
        this.softwareEngineerScore = softwareEngineerScore;
        this.completedAt = LocalDateTime.now();
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

    public int getAiMlScore() {
        return aiMlScore;
    }

    public void setAiMlScore(int aiMlScore) {
        this.aiMlScore = aiMlScore;
    }

    public int getDataScientistScore() {
        return dataScientistScore;
    }

    public void setDataScientistScore(int dataScientistScore) {
        this.dataScientistScore = dataScientistScore;
    }

    public int getSoftwareEngineerScore() {
        return softwareEngineerScore;
    }

    public void setSoftwareEngineerScore(int softwareEngineerScore) {
        this.softwareEngineerScore = softwareEngineerScore;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}