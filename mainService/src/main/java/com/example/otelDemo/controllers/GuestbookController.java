package com.example.otelDemo.controllers;

import com.example.otelDemo.dto.GuestbookDto;
import com.example.otelDemo.service.GuestbookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GuestbookController {

    private final GuestbookService service;

    public GuestbookController(GuestbookService service) {
        this.service = service;
    }

    @GetMapping("/guestbook")
    public String showGuestbook(Model model) {
        model.addAttribute("entries", service.findAll());
        model.addAttribute("newEntry", new GuestbookDto());
        return "guestbook";
    }

    @PostMapping("/guestbook")
    public String addEntry(@ModelAttribute("newEntry") GuestbookDto form) {
        service.addEntry(form.getName(), form.getMessage());
        return "redirect:/guestbook";
    }
}
