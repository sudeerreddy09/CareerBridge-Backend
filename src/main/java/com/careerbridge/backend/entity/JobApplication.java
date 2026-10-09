package com.careerbridge.backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "job_applications",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"user_id", "job_id"}
        )
    }
)
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "job_id", nullable = false)
    private Long jobId;

    @Column(nullable = false)
    private String status;

    private String resumeUrl;

    @Column(length = 2000)
    private String coverLetter;

    private LocalDateTime appliedAt;


    public JobApplication() {
    }


    public JobApplication(
            Long userId,
            Long jobId,
            String status,
            String resumeUrl,
            String coverLetter
    ) {
        this.userId = userId;
        this.jobId = jobId;
        this.status = status;
        this.resumeUrl = resumeUrl;
        this.coverLetter = coverLetter;
        this.appliedAt = LocalDateTime.now();
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


    public Long getJobId() {
        return jobId;
    }


    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public String getResumeUrl() {
        return resumeUrl;
    }


    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }


    public String getCoverLetter() {
        return coverLetter;
    }


    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }


    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }


    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }
}