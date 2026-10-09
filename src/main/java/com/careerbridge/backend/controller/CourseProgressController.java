package com.careerbridge.backend.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.careerbridge.backend.entity.CourseProgress;
import com.careerbridge.backend.entity.Notification;
import com.careerbridge.backend.repository.CourseProgressRepository;
import com.careerbridge.backend.repository.NotificationRepository;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/course-progress")
public class CourseProgressController {

    private final CourseProgressRepository progressRepository;
    private final NotificationRepository notificationRepository;

    public CourseProgressController(
            CourseProgressRepository progressRepository,
            NotificationRepository notificationRepository
    ) {
        this.progressRepository = progressRepository;
        this.notificationRepository = notificationRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveProgress(
            @RequestBody CourseProgress progress
    ) {

        CourseProgress existing =
                progressRepository
                        .findByUserIdAndCourseId(
                                progress.getUserId(),
                                progress.getCourseId()
                        )
                        .orElse(null);

        if (existing != null) {

            int oldProgress = existing.getProgress();

            existing.setProgress(progress.getProgress());
            existing.setCurrentLesson(progress.getCurrentLesson());
            existing.setUpdatedAt(LocalDateTime.now());

            CourseProgress saved =
                    progressRepository.save(existing);

            /*
             * Create the completion notification only when
             * the course changes from incomplete to completed.
             */
            if (oldProgress < 100 && progress.getProgress() >= 100) {

                createCourseCompletionNotification(progress);
            }

            return ResponseEntity.ok(saved);
        }

        progress.setUpdatedAt(LocalDateTime.now());

        CourseProgress saved =
                progressRepository.save(progress);

        /*
         * New progress record can be immediately completed.
         */
        if (progress.getProgress() >= 100) {

            createCourseCompletionNotification(progress);
        }

        return ResponseEntity.ok(saved);
    }

    @GetMapping("/user/{userId}/course/{courseId}")
    public ResponseEntity<?> getProgress(
            @PathVariable Long userId,
            @PathVariable Long courseId
    ) {

        return progressRepository
                .findByUserIdAndCourseId(userId, courseId)
                .map(ResponseEntity::ok)
                .orElse(
                        ResponseEntity.notFound().build()
                );
    }

    /*
     * Creates exactly one course-completion notification
     * for the current completion event.
     */
    private void createCourseCompletionNotification(
            CourseProgress progress
    ) {

        String courseName =
                getCourseName(progress.getCourseId());

        String message =
                "Congratulations! You have completed "
                        + courseName
                        + ".";

        /*
         * Prevent duplicate notifications if the frontend
         * sends the completion request more than once.
         */
        boolean alreadyExists =
                notificationRepository
                        .existsByUserIdAndTitleAndMessageAndType(
                                progress.getUserId(),
                                "Course completed",
                                message,
                                "COURSE"
                        );

        if (alreadyExists) {
            return;
        }

        Notification notification = new Notification(
                progress.getUserId(),
                "Course completed",
                message,
                "COURSE"
        );

        notificationRepository.save(notification);
    }

    /*
     * Course names used by the current CareerBridge
     * course IDs.
     */
    private String getCourseName(Long courseId) {

        if (courseId == null) {
            return "your course";
        }

        switch (courseId.intValue()) {

            case 1:
                return "Python for Beginners";

            case 2:
                return "Machine Learning Fundamentals";

            case 3:
                return "Data Structures & Algorithms";

            case 4:
                return "Data Science Essentials";

            case 5:
                return "Deep Learning & Neural Networks";

            case 6:
                return "Career & Interview Preparation";

            default:
                return "your course";
        }
    }
}