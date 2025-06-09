package com.example.job_portal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationDTO {
    private Long id;
    private Long userId;
    private String userName;
    private Long jobId;
    private String jobTitle;
    private String status;
    private String coverLetter;
    private LocalDateTime applicationDate;
    private LocalDateTime lastUpdated;
    private LocalDateTime interviewDate;
    private String interviewType;
    private String interviewLocation;
    private String interviewNotes;
    private String rejectionReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
