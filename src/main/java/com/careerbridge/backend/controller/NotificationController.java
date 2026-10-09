package com.careerbridge.backend.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.careerbridge.backend.entity.Notification;
import com.careerbridge.backend.repository.NotificationRepository;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationRepository notificationRepository;

    public NotificationController(
            NotificationRepository notificationRepository
    ) {
        this.notificationRepository = notificationRepository;
    }

    // Get all notifications for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getUserNotifications(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                notificationRepository
                        .findByUserIdOrderByCreatedAtDesc(userId)
        );
    }

    // Get unread notifications
    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<Notification>> getUnreadNotifications(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                notificationRepository
                        .findByUserIdAndReadFalseOrderByCreatedAtDesc(userId)
        );
    }

    // Get unread notification count
    @GetMapping("/user/{userId}/unread-count")
    public ResponseEntity<Long> getUnreadCount(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                notificationRepository
                        .countByUserIdAndReadFalse(userId)
        );
    }

    // Create a notification
    @PostMapping("/create")
    public ResponseEntity<?> createNotification(
            @RequestBody Notification notification
    ) {
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        return ResponseEntity.ok(
                notificationRepository.save(notification)
        );
    }

    // Mark one notification as read
    @PutMapping("/{notificationId}/read")
    public ResponseEntity<?> markAsRead(
            @PathVariable Long notificationId
    ) {
        Notification notification =
                notificationRepository
                        .findById(notificationId)
                        .orElse(null);

        if (notification == null) {
            return ResponseEntity.notFound().build();
        }

        notification.setRead(true);

        return ResponseEntity.ok(
                notificationRepository.save(notification)
        );
    }

    // Mark all notifications as read for a user
    @PutMapping("/user/{userId}/read-all")
    public ResponseEntity<?> markAllAsRead(
            @PathVariable Long userId
    ) {
        List<Notification> notifications =
                notificationRepository
                        .findByUserIdOrderByCreatedAtDesc(userId);

        for (Notification notification : notifications) {
            notification.setRead(true);
        }

        notificationRepository.saveAll(notifications);

        return ResponseEntity.ok(
                "All notifications marked as read"
        );
    }

    // Delete one notification
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<?> deleteNotification(
            @PathVariable Long notificationId
    ) {
        if (!notificationRepository.existsById(notificationId)) {
            return ResponseEntity.notFound().build();
        }

        notificationRepository.deleteById(notificationId);

        return ResponseEntity.ok(
                "Notification deleted successfully"
        );
    }
}