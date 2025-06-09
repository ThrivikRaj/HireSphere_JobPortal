package com.example.job_portal.controller;

import com.example.job_portal.dto.RegisterRequest;
import com.example.job_portal.model.User;
import com.example.job_portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        try {
            if (request.getEmail() == null || request.getPassword() == null) {
                return ResponseEntity.badRequest().body("Email and password are required");
            }
            if (userService.findByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("Email already exists");
            }
            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail().toLowerCase().trim());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(request.getRole() != null ? request.getRole() : "JOB_SEEKER");
            User savedUser = userService.save(user);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody RegisterRequest request) {
        try {
            if (request.getEmail() == null || request.getPassword() == null) {
                return ResponseEntity.badRequest().body("Email and password are required");
            }

            User user = userService.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Invalid email or password"));

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return ResponseEntity.badRequest().body("Invalid email or password");
            }

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentUser(@RequestParam String email) {
        try {
            User user = userService.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}