package com.example.otelDemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RestController
public class HealthController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        try {
            String databaseHealth = restTemplate.getForObject("http://dataBaseService:8082/health", String.class);
            if (databaseHealth != null && databaseHealth.contains("healthy")) {
                return ResponseEntity.ok("GuestBook Service is healthy");
            } else {
                return ResponseEntity.status(500).body("GuestBook Service is not healthy: Database is unreachable");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("GuestBook Service is not healthy: " + e.getMessage());
        }
    }
}
