package com.example.otelDemo.model;

import java.time.LocalDateTime;

public class GuestbookEntry {
    private String name;
    private String message;
    private LocalDateTime timestamp;

    public GuestbookEntry() {}

    public GuestbookEntry(String name, String message, LocalDateTime timestamp) {
        this.name = name;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
