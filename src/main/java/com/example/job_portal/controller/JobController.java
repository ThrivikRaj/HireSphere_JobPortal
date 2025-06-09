package com.example.job_portal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.service.JobService;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public List<JobDTO> getJobs() {
        return jobService.getAllJobs();
    }

    @PostMapping
    public ResponseEntity<JobDTO> createJob(@RequestBody JobDTO jobDTO) {
        JobDTO savedJob = jobService.createJob(jobDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedJob);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobDTO> updateJob(@PathVariable Long id, @RequestBody JobDTO jobDTO) {
        JobDTO updatedJob = jobService.updateJob(id, jobDTO);
        return ResponseEntity.ok(updatedJob);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.ok("Job deleted successfully.");
    }

    @GetMapping("/{id}")
    public JobDTO getJobById(@PathVariable Long id) {
        return jobService.getJobById(id);
    }

    @GetMapping("/company/{companyId}")
    public List<JobDTO> getJobsByCompany(@PathVariable Long companyId) {
        return jobService.getJobsByCompany(companyId);
    }

    @GetMapping("/search")
    public List<JobDTO> searchJobs(@RequestParam String keyword) {
        return jobService.searchJobs(keyword);
    }
}
