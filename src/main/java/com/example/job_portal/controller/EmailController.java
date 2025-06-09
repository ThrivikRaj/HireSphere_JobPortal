package com.example.job_portal.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.job_portal.service.EmailService;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public String sendTestEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {
        emailService.sendEmail(to, subject, body);
        return "Email sent successfully to " + to;
    }
}
