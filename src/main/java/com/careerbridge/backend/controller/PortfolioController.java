package com.careerbridge.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.careerbridge.backend.entity.Portfolio;
import com.careerbridge.backend.repository.PortfolioRepository;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final PortfolioRepository portfolioRepository;

    public PortfolioController(
            PortfolioRepository portfolioRepository
    ) {
        this.portfolioRepository = portfolioRepository;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getPortfolio(
            @PathVariable Long userId
    ) {

        return portfolioRepository
                .findByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(
                        ResponseEntity.notFound().build()
                );
    }

    @PostMapping("/save")
    public ResponseEntity<?> savePortfolio(
            @RequestBody Portfolio portfolio
    ) {

        Portfolio existing =
                portfolioRepository
                        .findByUserId(portfolio.getUserId())
                        .orElse(null);

        if (existing != null) {

            existing.setCareerGoal(
                    portfolio.getCareerGoal()
            );

            existing.setBio(
                    portfolio.getBio()
            );

            existing.setGithubUrl(
                    portfolio.getGithubUrl()
            );

            existing.setLinkedinUrl(
                    portfolio.getLinkedinUrl()
            );

            existing.setSkills(
                    portfolio.getSkills()
            );

            existing.setProjects(
                    portfolio.getProjects()
            );

            return ResponseEntity.ok(
                    portfolioRepository.save(existing)
            );
        }

        return ResponseEntity.ok(
                portfolioRepository.save(portfolio)
        );
    }
}