package com.example.otelDemo.service;

import com.example.otelDemo.dto.GuestbookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GuestbookService {

    private final RestTemplate restTemplate;

    @Autowired
    public GuestbookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<GuestbookDto> findAll() {
        ResponseEntity<List<GuestbookDto>> response = restTemplate.exchange(
                "http://dataBaseService:8082/api/database/entries",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GuestbookDto>>() {}
        );
        return response.getBody();
    }

    public GuestbookDto addEntry(String name, String message) {
        GuestbookDto entry = new GuestbookDto(name, message, LocalDateTime.now());
        ResponseEntity<GuestbookDto> response = restTemplate.postForEntity(
                "http://dataBaseService:8082/api/database/entries",
                entry,
                GuestbookDto.class
        );
        return response.getBody();
    }
}

