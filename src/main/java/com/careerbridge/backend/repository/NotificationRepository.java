package com.careerbridge.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.careerbridge.backend.entity.Notification;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification> findByUserIdOrderByCreatedAtDesc(
            Long userId
    );

    List<Notification> findByUserIdAndReadFalseOrderByCreatedAtDesc(
            Long userId
    );

    long countByUserIdAndReadFalse(
            Long userId
    );

    boolean existsByUserIdAndTitleAndMessageAndType(
            Long userId,
            String title,
            String message,
            String type
    );
}