package com.example.otelDemo.controllers;

import com.example.otelDemo.model.GuestbookEntry;
import com.example.otelDemo.service.GuestbookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guestbook")
public class GuestbookController {

    private final GuestbookService guestbookService;

    public GuestbookController(GuestbookService guestbookService) {
        this.guestbookService = guestbookService;
    }

    // Get all entries
    @GetMapping("/entries")
    public ResponseEntity<List<GuestbookEntry>> getAllEntries() {
        return ResponseEntity.ok(guestbookService.findAll());
    }

    // Add a new entry
    @PostMapping("/entries")
    public ResponseEntity<String> addEntry(@RequestParam String name, @RequestParam String message) {
        guestbookService.addEntry(name, message);
        return ResponseEntity.ok("Guestbook entry added successfully!");
    }
}
