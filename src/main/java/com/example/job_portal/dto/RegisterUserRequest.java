package com.example.job_portal.dto;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private String name;
    private String email;
    private String password;
    private String role;
}
