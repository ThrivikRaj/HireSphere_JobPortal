package com.example.job_portal.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobApplication {

    public enum ApplicationStatus {
        PENDING,
        REVIEWING,
        ACCEPTED,
        REJECTED,
        WITHDRAWN,
        APPLIED,
        INTERVIEW
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(columnDefinition = "TEXT")
    private String coverLetter;

    @Column
    private LocalDateTime applicationDate;

    @Column
    private LocalDateTime lastUpdated;

    @Column
    private LocalDateTime interviewDate;

    @Column
    private String interviewType;

    @Column
    private String interviewLocation;

    @Column(columnDefinition = "TEXT")
    private String interviewNotes;

    @Column(columnDefinition = "TEXT")
    private String rejectionReason;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        applicationDate = LocalDateTime.now();
        lastUpdated = LocalDateTime.now();
        if (status == null) {
            status = ApplicationStatus.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        lastUpdated = LocalDateTime.now();
    }
}
