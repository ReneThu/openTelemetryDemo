package com.example.otelDemo.service;

import com.example.otelDemo.model.GuestbookEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class GuestbookService {

    private final RestTemplate restTemplate;

    @Autowired
    public GuestbookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<GuestbookEntry> findAll() {
        ResponseEntity<List<GuestbookEntry>> response = restTemplate.exchange(
                "http://dataBaseService:8082/api/database/entries",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GuestbookEntry>>() {}
        );
        return response.getBody();
    }

    public void addEntry(String name, String message) {
        GuestbookEntry entry = new GuestbookEntry(name, message, LocalDateTime.now());
        restTemplate.postForEntity("http://dataBaseService:8082/api/database/entries", entry, String.class);
    }
}

