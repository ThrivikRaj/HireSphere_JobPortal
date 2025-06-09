package com.example.job_portal.repository;

import com.example.job_portal.model.JobApplication;
import com.example.job_portal.model.JobApplication.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByUserId(Long userId);
    List<JobApplication> findByJobId(Long jobId);
    List<JobApplication> findByStatus(ApplicationStatus status);
    List<JobApplication> findByInterviewDateBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT ja FROM JobApplication ja WHERE ja.user.id = :userId AND ja.status = :status")
    List<JobApplication> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") ApplicationStatus status);
    
    @Query("SELECT ja FROM JobApplication ja WHERE ja.job.id = :jobId AND ja.status = :status")
    List<JobApplication> findByJobIdAndStatus(@Param("jobId") Long jobId, @Param("status") ApplicationStatus status);
    
    @Query("SELECT ja FROM JobApplication ja WHERE ja.interviewDate IS NOT NULL AND ja.interviewDate > :now")
    List<JobApplication> findUpcomingInterviews(@Param("now") LocalDateTime now);
}
