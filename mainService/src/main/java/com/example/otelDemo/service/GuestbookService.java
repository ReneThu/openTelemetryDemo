package com.example.otelDemo.service;

import com.example.otelDemo.dto.GuestbookDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class GuestbookService {

    private final RestTemplate restTemplate;
    private final MessageService messageProducer;
    private final String guestbookServiceUrl = "http://guestBookService:8081/api/guestbook";

    public GuestbookService(RestTemplate restTemplate, MessageService messageProducer) {
        this.restTemplate = restTemplate;
        this.messageProducer = messageProducer;
    }

    public List<GuestbookDto> findAll() {
        try {
            GuestbookDto[] entries = restTemplate.getForObject(
                    guestbookServiceUrl + "/entries", GuestbookDto[].class);
            if (entries != null) {
                List<GuestbookDto> entryList = Arrays.asList(entries);
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
            GuestbookDto entry = restTemplate.postForEntity(
                    guestbookServiceUrl + "/entries?name=" + name + "&message=" + message,
                    null, GuestbookDto.class).getBody();
            messageProducer.sendNotification(entry.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
