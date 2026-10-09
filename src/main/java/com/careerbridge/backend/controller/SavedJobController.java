package com.careerbridge.backend.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.careerbridge.backend.entity.SavedJob;
import com.careerbridge.backend.repository.SavedJobRepository;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/saved-jobs")
public class SavedJobController {

    private final SavedJobRepository savedJobRepository;

    public SavedJobController(
            SavedJobRepository savedJobRepository
    ) {
        this.savedJobRepository = savedJobRepository;
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SavedJob>> getSavedJobs(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                savedJobRepository
                        .findByUserIdOrderBySavedAtDesc(userId)
        );
    }


    @PostMapping("/save")
    public ResponseEntity<?> saveJob(
            @RequestBody SavedJob savedJob
    ) {

        boolean alreadySaved =
                savedJobRepository
                        .existsByUserIdAndJobId(
                                savedJob.getUserId(),
                                savedJob.getJobId()
                        );

        if (alreadySaved) {

            return ResponseEntity
                    .badRequest()
                    .body("Job is already saved");
        }

        savedJob.setSavedAt(
                LocalDateTime.now()
        );

        return ResponseEntity.ok(
                savedJobRepository.save(savedJob)
        );
    }


    @DeleteMapping("/user/{userId}/job/{jobId}")
    public ResponseEntity<?> removeSavedJob(
            @PathVariable Long userId,
            @PathVariable Long jobId
    ) {

        boolean exists =
                savedJobRepository
                        .existsByUserIdAndJobId(
                                userId,
                                jobId
                        );

        if (!exists) {

            return ResponseEntity
                    .notFound()
                    .build();
        }


        savedJobRepository.deleteByUserIdAndJobId(
                userId,
                jobId
        );


        return ResponseEntity.ok(
                "Job removed from saved jobs"
        );
    }
}