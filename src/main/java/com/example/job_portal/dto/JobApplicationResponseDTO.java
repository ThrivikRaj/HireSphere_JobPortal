package com.example.job_portal.dto;

import com.example.job_portal.model.JobApplication.ApplicationStatus;
import lombok.Data;

@Data
public class JobApplicationResponseDTO {
    private Long id;
    private String applicantName;
    private String applicantEmail;
    private String resumeDownloadUrl;
    private ApplicationStatus status;
    private String jobTitle;
}
