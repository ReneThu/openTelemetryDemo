package com.example.otelDemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import javax.sql.DataSource;
import java.sql.Connection;

@RestController
public class HealthController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(1)) {
                return ResponseEntity.ok("Database Service is healthy");
            } else {
                return ResponseEntity.status(500).body("Database Service is not healthy");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Database Service is not healthy: " + e.getMessage());
        }
    }
}
