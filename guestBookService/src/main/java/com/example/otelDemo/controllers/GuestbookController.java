package com.example.otelDemo.controllers;

import com.example.otelDemo.dto.GuestbookDto;
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

    @GetMapping("/entries")
    public ResponseEntity<List<GuestbookDto>> getAllEntries() {
        return ResponseEntity.ok(guestbookService.findAll());
    }

    @PostMapping("/entries")
    public ResponseEntity<GuestbookDto> addEntry(@RequestParam String name, @RequestParam String message) {
        GuestbookDto entry = guestbookService.addEntry(name, message);
        return ResponseEntity.ok(entry);
    }
}
