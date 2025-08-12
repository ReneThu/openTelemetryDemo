package com.example.otelDemo.service;

import com.example.otelDemo.model.GuestbookEntry;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class GuestbookService {

    private final RestTemplate restTemplate;

    private final String guestbookServiceUrl = "http://localhost:8081/api/guestbook";

    public GuestbookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<GuestbookEntry> findAll() {
        try {
            GuestbookEntry[] entries = restTemplate.getForObject(
                    guestbookServiceUrl + "/entries", GuestbookEntry[].class);
            if (entries != null) {
                List<GuestbookEntry> entryList = Arrays.asList(entries);
                Collections.reverse(entryList); // newest first
                return entryList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public void addEntry(String name, String message) {
        try {
            restTemplate.postForObject(
                    guestbookServiceUrl + "/entries?name=" + name + "&message=" + message,
                    null, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
