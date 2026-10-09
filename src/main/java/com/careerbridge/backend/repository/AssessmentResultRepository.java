package com.careerbridge.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.careerbridge.backend.entity.AssessmentResult;

public interface AssessmentResultRepository
        extends JpaRepository<AssessmentResult, Long> {

    Optional<AssessmentResult> findTopByUserIdOrderByCompletedAtDesc(
            Long userId
    );
}