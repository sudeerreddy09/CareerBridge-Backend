package com.careerbridge.backend.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.careerbridge.backend.entity.JobApplication;
import com.careerbridge.backend.entity.Notification;
import com.careerbridge.backend.repository.JobApplicationRepository;
import com.careerbridge.backend.repository.NotificationRepository;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/applications")
public class JobApplicationController {

    private final JobApplicationRepository applicationRepository;
    private final NotificationRepository notificationRepository;

    public JobApplicationController(
            JobApplicationRepository applicationRepository,
            NotificationRepository notificationRepository
    ) {
        this.applicationRepository = applicationRepository;
        this.notificationRepository = notificationRepository;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<JobApplication>> getUserApplications(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                applicationRepository.findByUserIdOrderByAppliedAtDesc(userId)
        );
    }

    @PostMapping("/apply")
    public ResponseEntity<?> applyForJob(
            @RequestBody JobApplication application
    ) {

        boolean alreadyApplied =
                applicationRepository.existsByUserIdAndJobId(
                        application.getUserId(),
                        application.getJobId()
                );

        if (alreadyApplied) {
            return ResponseEntity.badRequest()
                    .body("You have already applied for this job");
        }

        application.setStatus("APPLIED");
        application.setAppliedAt(LocalDateTime.now());

        JobApplication savedApplication =
                applicationRepository.save(application);

        String jobTitle =
                getJobTitle(application.getJobId());

        Notification notification = new Notification(
                application.getUserId(),
                "Application submitted",
                "Your application for "
                        + jobTitle
                        + " has been submitted successfully.",
                "APPLICATION"
        );

        notificationRepository.save(notification);

        return ResponseEntity.ok(savedApplication);
    }

    @GetMapping("/user/{userId}/job/{jobId}")
    public ResponseEntity<?> checkApplication(
            @PathVariable Long userId,
            @PathVariable Long jobId
    ) {
        return applicationRepository
                .findByUserIdAndJobId(userId, jobId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{applicationId}/status")
    public ResponseEntity<?> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestParam String status
    ) {

        JobApplication application =
                applicationRepository
                        .findById(applicationId)
                        .orElse(null);

        if (application == null) {
            return ResponseEntity.notFound().build();
        }

        String normalizedStatus =
                status.trim().toUpperCase();

        if (
                !normalizedStatus.equals("APPLIED")
                && !normalizedStatus.equals("UNDER_REVIEW")
                && !normalizedStatus.equals("SHORTLISTED")
                && !normalizedStatus.equals("INTERVIEW")
                && !normalizedStatus.equals("SELECTED")
                && !normalizedStatus.equals("REJECTED")
        ) {
            return ResponseEntity.badRequest()
                    .body("Invalid application status");
        }

        application.setStatus(normalizedStatus);

        JobApplication savedApplication =
                applicationRepository.save(application);

        String jobTitle =
                getJobTitle(application.getJobId());

        String title;
        String notificationMessage;

        switch (normalizedStatus) {

            case "UNDER_REVIEW":
                title = "Application under review";
                notificationMessage =
                        "Your application for "
                                + jobTitle
                                + " is now under review.";
                break;

            case "SHORTLISTED":
                title = "Application shortlisted";
                notificationMessage =
                        "Congratulations! Your application for "
                                + jobTitle
                                + " has been shortlisted.";
                break;

            case "INTERVIEW":
                title = "Interview stage reached";
                notificationMessage =
                        "Your application for "
                                + jobTitle
                                + " has moved to the interview stage.";
                break;

            case "SELECTED":
                title = "Application selected";
                notificationMessage =
                        "Congratulations! Your application for "
                                + jobTitle
                                + " has been selected.";
                break;

            case "REJECTED":
                title = "Application update";
                notificationMessage =
                        "Your application for "
                                + jobTitle
                                + " was not selected at this time.";
                break;

            case "APPLIED":
                title = "Application updated";
                notificationMessage =
                        "Your application for "
                                + jobTitle
                                + " is marked as applied.";
                break;

            default:
                title = "Application update";
                notificationMessage =
                        "Your application for "
                                + jobTitle
                                + " has been updated.";
        }

        Notification notification = new Notification(
                application.getUserId(),
                title,
                notificationMessage,
                "APPLICATION"
        );

        notificationRepository.save(notification);

        return ResponseEntity.ok(savedApplication);
    }

    private String getJobTitle(Long jobId) {

        if (jobId == null) {
            return "the selected job";
        }

        switch (jobId.intValue()) {

            case 1:
                return "Machine Learning Engineer";

            case 2:
                return "Data Scientist";

            case 3:
                return "Software Engineer";

            case 4:
                return "AI Engineer";

            case 5:
                return "Backend Developer";

            case 6:
                return "Junior Data Analyst";

            default:
                return "the selected job";
        }
    }
}