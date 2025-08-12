package com.example.otelDemo.service;

import com.example.otelDemo.model.GuestbookEntry;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class GuestbookService {

    private final List<GuestbookEntry> entries = new CopyOnWriteArrayList<>();

    public List<GuestbookEntry> findAll() {
        List<GuestbookEntry> copy = new ArrayList<>(entries);
        Collections.reverse(copy);               // newest first
        return copy;
    }

    public void addEntry(String name, String message) {
        GuestbookEntry entry =
                new GuestbookEntry(name, message, LocalDateTime.now());
        entries.add(entry);
    }
}
