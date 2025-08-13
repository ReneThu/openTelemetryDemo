package com.example.otelDemo.controllers;

import com.example.otelDemo.entry.GuestbookEntry;
import com.example.otelDemo.repository.GuestbookEntryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/database")
public class DataBaseController {

    private final GuestbookEntryRepository repository;

    public DataBaseController(GuestbookEntryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/entries")
    public ResponseEntity<List<GuestbookEntry>> getAllEntries() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping("/entries")
    public ResponseEntity<GuestbookEntry> addEntry(@RequestBody GuestbookEntry entry) {
        entry.setTimestamp(LocalDateTime.now());
        GuestbookEntry savedEntry = repository.save(entry);
        return ResponseEntity.ok(savedEntry);
    }
}
