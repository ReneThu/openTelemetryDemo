package com.example.otelDemo.controllers;

import com.example.otelDemo.model.GuestbookEntry;
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
        model.addAttribute("newEntry", new GuestbookEntry());   // backing-object for form
        return "guestbook";
    }

    @PostMapping("/guestbook")
    public String addEntry(@ModelAttribute("newEntry") GuestbookEntry form) {
        service.addEntry(form.getName(), form.getMessage());
        return "redirect:/guestbook";
    }
}
