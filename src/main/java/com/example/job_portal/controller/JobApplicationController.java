package com.example.job_portal.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.job_portal.dto.JobApplicationDTO;
import com.example.job_portal.model.JobApplication.ApplicationStatus;
import com.example.job_portal.service.JobApplicationService;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @PostMapping
    public ResponseEntity<JobApplicationDTO> applyForJob(@RequestBody JobApplicationDTO jobApplicationDTO) {
        try {
            JobApplicationDTO savedApplication = jobApplicationService.applyForJob(jobApplicationDTO);
            return ResponseEntity.ok(savedApplication);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public List<JobApplicationDTO> getAllApplications() {
        return jobApplicationService.getAllApplications();
    }

    @GetMapping("/{id}")
    public JobApplicationDTO getApplicationById(@PathVariable Long id) {
        return jobApplicationService.getApplicationById(id);
    }

    @GetMapping("/job/{jobId}")
    public List<JobApplicationDTO> getApplicationsByJob(@PathVariable Long jobId) {
        return jobApplicationService.getApplicationsByJob(jobId);
    }

    @GetMapping("/user/{userId}")
    public List<JobApplicationDTO> getApplicationsByUser(@PathVariable Long userId) {
        return jobApplicationService.getApplicationsByUser(userId);
    }

    @GetMapping("/status/{status}")
    public List<JobApplicationDTO> getApplicationsByStatus(@PathVariable ApplicationStatus status) {
        return jobApplicationService.getApplicationsByStatus(status);
    }

    @GetMapping("/user/{userId}/status/{status}")
    public List<JobApplicationDTO> getApplicationsByUserAndStatus(
            @PathVariable Long userId,
            @PathVariable ApplicationStatus status) {
        return jobApplicationService.getApplicationsByUserAndStatus(userId, status);
    }

    @GetMapping("/job/{jobId}/status/{status}")
    public List<JobApplicationDTO> getApplicationsByJobAndStatus(
            @PathVariable Long jobId,
            @PathVariable ApplicationStatus status) {
        return jobApplicationService.getApplicationsByJobAndStatus(jobId, status);
    }

    @PutMapping("/{id}/status")
    public JobApplicationDTO updateApplicationStatus(
            @PathVariable Long id,
            @RequestParam ApplicationStatus status) {
        return jobApplicationService.updateApplicationStatus(id, status);
    }

    @PutMapping("/{id}/interview")
    public JobApplicationDTO scheduleInterview(
            @PathVariable Long id,
            @RequestParam String interviewDate,
            @RequestParam String interviewType,
            @RequestParam String interviewLocation) {
        return jobApplicationService.scheduleInterview(id, 
            java.time.LocalDateTime.parse(interviewDate), 
            interviewType, 
            interviewLocation);
    }

    @PutMapping("/{id}/interview-notes")
    public JobApplicationDTO addInterviewNotes(
            @PathVariable Long id,
            @RequestParam String notes) {
        return jobApplicationService.addInterviewNotes(id, notes);
    }

    @PutMapping("/{id}/rejection")
    public JobApplicationDTO addRejectionReason(
            @PathVariable Long id,
            @RequestParam String reason) {
        return jobApplicationService.addRejectionReason(id, reason);
    }
}
