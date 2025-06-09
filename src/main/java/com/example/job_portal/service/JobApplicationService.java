package com.example.job_portal.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.job_portal.dto.JobApplicationDTO;
import com.example.job_portal.model.Job;
import com.example.job_portal.model.JobApplication;
import com.example.job_portal.model.JobApplication.ApplicationStatus;
import com.example.job_portal.model.User;
import com.example.job_portal.repository.JobApplicationRepository;
import com.example.job_portal.repository.JobRepository;
import com.example.job_portal.repository.UserRepository;
import com.example.job_portal.util.EntityMapper;

@Service
public class JobApplicationService {
    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private EmailService emailService;

    private final String uploadDir = "uploads/resumes/";

    public JobApplicationDTO applyForJob(JobApplicationDTO jobApplicationDTO) throws IOException {
        Job job = jobRepository.findById(jobApplicationDTO.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));

        User user = userRepository.findById(jobApplicationDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        JobApplication jobApplication = new JobApplication();
        jobApplication.setJob(job);
        jobApplication.setUser(user);
        jobApplication.setCoverLetter(jobApplicationDTO.getCoverLetter());
        jobApplication.setStatus(ApplicationStatus.APPLIED);
        jobApplication.setApplicationDate(LocalDateTime.now());
        jobApplication.setLastUpdated(LocalDateTime.now());

        JobApplication savedApplication = jobApplicationRepository.save(jobApplication);

        // Try to send email notifications, but don't fail if email sending fails
        try {
            String applicantSubject = "Job Application Received!";
            String applicantBody = "<h2>Dear " + user.getName() + ",</h2>"
                    + "<p>Your application for the position of <strong>" + job.getTitle() + "</strong> has been received.</p>"
                    + "<p>We will review your application and get back to you soon.</p>"
                    + "<br><p>Best Regards,<br>Job Portal Team</p>";
            emailService.sendEmail(user.getEmail(), applicantSubject, applicantBody);

            String recruiterSubject = "New Job Application for " + job.getTitle();
            String recruiterBody = "<h2>New Job Application</h2>"
                    + "<p>A new application has been submitted for the position of <strong>" + job.getTitle() + "</strong>.</p>"
                    + "<p><strong>Applicant Name:</strong> " + user.getName() + "</p>"
                    + "<p><strong>Email:</strong> " + user.getEmail() + "</p>"
                    + "<p>Check your dashboard for more details.</p>"
                    + "<br><p>Best Regards,<br>Job Portal Team</p>";
            emailService.sendEmail(job.getRecruiterEmail(), recruiterSubject, recruiterBody);
        } catch (Exception e) {
            // Log the error but don't throw it
            System.err.println("Failed to send email notification: " + e.getMessage());
        }

        return entityMapper.toJobApplicationDTO(savedApplication);
    }

    public JobApplicationDTO updateApplicationStatus(Long id, ApplicationStatus status) {
        JobApplication jobApplication = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        jobApplication.setStatus(status);
        jobApplication.setLastUpdated(LocalDateTime.now());
        JobApplication updatedApplication = jobApplicationRepository.save(jobApplication);

        // Try to send email notification for status change, but don't fail if email sending fails
        try {
            if (status == ApplicationStatus.ACCEPTED) {
                String subject = "Job Application Accepted!";
                String body = "<h2>Dear " + jobApplication.getUser().getName() + ",</h2>"
                        + "<p>Congratulations! Your application for the position of <strong>" + jobApplication.getJob().getTitle() + "</strong> has been accepted.</p>"
                        + "<p>The recruiter will contact you soon.</p>"
                        + "<br><p>Best Regards,<br>Job Portal Team</p>";
                emailService.sendEmail(jobApplication.getUser().getEmail(), subject, body);
            } else if (status == ApplicationStatus.REJECTED) {
                String subject = "Job Application Status Update";
                String body = "<h2>Dear " + jobApplication.getUser().getName() + ",</h2>"
                        + "<p>Your application for the position of <strong>" + jobApplication.getJob().getTitle() + "</strong> has been reviewed.</p>"
                        + "<p>Unfortunately, we have decided to move forward with other candidates.</p>"
                        + "<p>We wish you the best in your job search.</p>"
                        + "<br><p>Best Regards,<br>Job Portal Team</p>";
                emailService.sendEmail(jobApplication.getUser().getEmail(), subject, body);
            }
        } catch (Exception e) {
            // Log the error but don't throw it
            System.err.println("Failed to send email notification: " + e.getMessage());
        }

        return entityMapper.toJobApplicationDTO(updatedApplication);
    }

    public List<JobApplicationDTO> getAllApplications() {
        return jobApplicationRepository.findAll().stream()
                .map(entityMapper::toJobApplicationDTO)
                .collect(Collectors.toList());
    }

    public JobApplicationDTO getApplicationById(Long id) {
        return jobApplicationRepository.findById(id)
                .map(entityMapper::toJobApplicationDTO)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));
    }

    public List<JobApplicationDTO> getApplicationsByUser(Long userId) {
        return jobApplicationRepository.findByUserId(userId).stream()
                .map(entityMapper::toJobApplicationDTO)
                .collect(Collectors.toList());
    }

    public List<JobApplicationDTO> getApplicationsByJob(Long jobId) {
        return jobApplicationRepository.findByJobId(jobId).stream()
                .map(entityMapper::toJobApplicationDTO)
                .collect(Collectors.toList());
    }

    public List<JobApplicationDTO> getApplicationsByStatus(ApplicationStatus status) {
        return jobApplicationRepository.findByStatus(status).stream()
                .map(entityMapper::toJobApplicationDTO)
                .collect(Collectors.toList());
    }

    public List<JobApplicationDTO> getApplicationsByUserAndStatus(Long userId, ApplicationStatus status) {
        return jobApplicationRepository.findByUserIdAndStatus(userId, status).stream()
                .map(entityMapper::toJobApplicationDTO)
                .collect(Collectors.toList());
    }

    public List<JobApplicationDTO> getApplicationsByJobAndStatus(Long jobId, ApplicationStatus status) {
        return jobApplicationRepository.findByJobIdAndStatus(jobId, status).stream()
                .map(entityMapper::toJobApplicationDTO)
                .collect(Collectors.toList());
    }

    public List<JobApplicationDTO> getUpcomingInterviews() {
        return jobApplicationRepository.findByInterviewDateBetween(LocalDateTime.now(), LocalDateTime.now().plusDays(7)).stream()
                .map(entityMapper::toJobApplicationDTO)
                .collect(Collectors.toList());
    }

    public JobApplicationDTO scheduleInterview(Long id, LocalDateTime interviewDate, String interviewType, String interviewLocation) {
        JobApplication jobApplication = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        jobApplication.setInterviewDate(interviewDate);
        jobApplication.setInterviewType(interviewType);
        jobApplication.setInterviewLocation(interviewLocation);
        jobApplication.setStatus(ApplicationStatus.INTERVIEW);
        jobApplication.setLastUpdated(LocalDateTime.now());

        JobApplication updatedApplication = jobApplicationRepository.save(jobApplication);

        // Try to send email notification for interview scheduling, but don't fail if email sending fails
        try {
            String subject = "Interview Scheduled";
            String body = "<h2>Dear " + jobApplication.getUser().getName() + ",</h2>"
                    + "<p>An interview has been scheduled for your application for the position of <strong>" + jobApplication.getJob().getTitle() + "</strong>.</p>"
                    + "<p><strong>Date:</strong> " + interviewDate + "</p>"
                    + "<p><strong>Type:</strong> " + interviewType + "</p>"
                    + "<p><strong>Location:</strong> " + interviewLocation + "</p>"
                    + "<br><p>Best Regards,<br>Job Portal Team</p>";
            emailService.sendEmail(jobApplication.getUser().getEmail(), subject, body);
        } catch (Exception e) {
            // Log the error but don't throw it
            System.err.println("Failed to send email notification: " + e.getMessage());
        }

        return entityMapper.toJobApplicationDTO(updatedApplication);
    }

    public JobApplicationDTO addInterviewNotes(Long id, String notes) {
        JobApplication jobApplication = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        jobApplication.setInterviewNotes(notes);
        jobApplication.setLastUpdated(LocalDateTime.now());

        JobApplication updatedApplication = jobApplicationRepository.save(jobApplication);
        return entityMapper.toJobApplicationDTO(updatedApplication);
    }

    public JobApplicationDTO addRejectionReason(Long id, String reason) {
        JobApplication jobApplication = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        jobApplication.setRejectionReason(reason);
        jobApplication.setStatus(ApplicationStatus.REJECTED);
        jobApplication.setLastUpdated(LocalDateTime.now());

        JobApplication updatedApplication = jobApplicationRepository.save(jobApplication);
        return entityMapper.toJobApplicationDTO(updatedApplication);
    }
}
