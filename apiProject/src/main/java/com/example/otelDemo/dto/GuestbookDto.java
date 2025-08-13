package com.example.otelDemo.dto;

import java.time.LocalDateTime;

public class GuestbookDto {
    private Long id;
    private String name;
    private String message;
    private LocalDateTime timestamp;

    public GuestbookDto() {
    }

    public GuestbookDto(String name, String message, LocalDateTime timestamp) {
        this.name = name;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
