package com.example.otelDemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HealthController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        try {
            String databaseHealth = restTemplate.getForObject("http://dataBaseService:8083/health", String.class);
            String guestHealth = restTemplate.getForObject("http://guestBookService:8081/health", String.class);

            if (databaseHealth != null && databaseHealth.contains("healthy") && guestHealth != null && guestHealth.contains("healthy")) {
                return ResponseEntity.ok("Main Service is healthy");
            } else {
                return ResponseEntity.status(500).body("Main Service is not healthy: Database or guestBook unreachable");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Main Service is not healthy: " + e.getMessage());
        }
    }
}
