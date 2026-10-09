package com.careerbridge.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CareerBridgeController {

    @GetMapping("/")
    public String home() {
        return "CareerBridge Backend is running successfully!";
    }

    @GetMapping("/api/health")
    public String health() {
        return "CareerBridge API is healthy!";
    }
}