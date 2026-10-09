package com.careerbridge.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.careerbridge.backend.entity.SavedJob;

public interface SavedJobRepository
        extends JpaRepository<SavedJob, Long> {

    List<SavedJob> findByUserIdOrderBySavedAtDesc(
            Long userId
    );

    Optional<SavedJob> findByUserIdAndJobId(
            Long userId,
            Long jobId
    );

    boolean existsByUserIdAndJobId(
            Long userId,
            Long jobId
    );

    @Transactional
    void deleteByUserIdAndJobId(
            Long userId,
            Long jobId
    );
}