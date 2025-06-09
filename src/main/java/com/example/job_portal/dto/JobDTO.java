package com.example.job_portal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {
    private Long id;
    private String recruiterEmail;
    private int experience;
    private String title;
    private Long companyId;
    private String companyName;
    private String location;
    private String description;
    private Double salary;
    private String employmentType;
    private String jobStatus;
    private LocalDateTime applicationDeadline;
    private Integer numberOfPositions;
    private String remoteWorkPolicy;
    private String benefits;
    private Set<SkillDTO> requiredSkills;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 