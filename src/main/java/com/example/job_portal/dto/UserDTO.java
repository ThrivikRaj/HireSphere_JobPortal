package com.example.job_portal.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String role;
    private String currentTitle;
    private String professionalSummary;
    private Integer yearsOfExperience;
    private String preferredJobTypes;
    private String preferredLocations;
    private Double expectedSalary;
}
