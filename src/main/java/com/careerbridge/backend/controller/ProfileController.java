package com.careerbridge.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.careerbridge.backend.entity.Profile;
import com.careerbridge.backend.repository.ProfileRepository;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileRepository profileRepository;

    public ProfileController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getProfile(
            @PathVariable Long userId
    ) {

        return profileRepository
                .findByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(
                        ResponseEntity.notFound().build()
                );
    }


    @PostMapping("/save")
    public ResponseEntity<?> saveProfile(
            @RequestBody Profile profile
    ) {

        Profile existing =
                profileRepository
                        .findByUserId(profile.getUserId())
                        .orElse(null);

        if (existing != null) {

            existing.setPhone(profile.getPhone());

            existing.setLocation(profile.getLocation());

            existing.setCareerGoal(
                    profile.getCareerGoal()
            );

            existing.setBio(
                    profile.getBio()
            );

            existing.setSkills(
                    profile.getSkills()
            );

            existing.setGithubUrl(
                    profile.getGithubUrl()
            );

            existing.setLinkedinUrl(
                    profile.getLinkedinUrl()
            );

            // Education

            existing.setDegree(
                    profile.getDegree()
            );

            existing.setCollege(
                    profile.getCollege()
            );

            existing.setBranch(
                    profile.getBranch()
            );

            existing.setGraduationYear(
                    profile.getGraduationYear()
            );

            existing.setCgpa(
                    profile.getCgpa()
            );

            return ResponseEntity.ok(
                    profileRepository.save(existing)
            );
        }


        return ResponseEntity.ok(
                profileRepository.save(profile)
        );
    }
}