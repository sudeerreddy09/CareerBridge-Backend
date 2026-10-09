package com.careerbridge.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.careerbridge.backend.entity.AssessmentResult;
import com.careerbridge.backend.entity.Notification;
import com.careerbridge.backend.repository.AssessmentResultRepository;
import com.careerbridge.backend.repository.NotificationRepository;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/assessment")
public class AssessmentController {

    private final AssessmentResultRepository assessmentRepository;
    private final NotificationRepository notificationRepository;

    public AssessmentController(
            AssessmentResultRepository assessmentRepository,
            NotificationRepository notificationRepository
    ) {
        this.assessmentRepository = assessmentRepository;
        this.notificationRepository = notificationRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveAssessment(
            @RequestBody AssessmentResult result
    ) {

        AssessmentResult saved =
                assessmentRepository.save(result);

        Notification notification = new Notification(
                result.getUserId(),
                "Career assessment completed",
                "Your career assessment has been completed successfully.",
                "ASSESSMENT"
        );

        notificationRepository.save(notification);

        return ResponseEntity.ok(saved);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getLatestAssessment(
            @PathVariable Long userId
    ) {
        return assessmentRepository
                .findTopByUserIdOrderByCompletedAtDesc(userId)
                .map(ResponseEntity::ok)
                .orElse(
                        ResponseEntity.notFound().build()
                );
    }
}