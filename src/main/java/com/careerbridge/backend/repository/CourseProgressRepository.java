package com.careerbridge.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.careerbridge.backend.entity.CourseProgress;

public interface CourseProgressRepository
        extends JpaRepository<CourseProgress, Long> {

    Optional<CourseProgress> findByUserIdAndCourseId(
            Long userId,
            Long courseId
    );
}