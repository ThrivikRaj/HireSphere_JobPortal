package com.example.job_portal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String recruiterEmail;

    @Column(nullable = false)
    private int experience;
    
    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double salary;

    @Column(nullable = false)
    private String employmentType; // Full-time, Part-time, Remote, etc.

    @Column(nullable = false)
    private String jobStatus; // Active, Closed, Draft

    @Column(nullable = false)
    private LocalDateTime applicationDeadline;

    @Column(nullable = false)
    private Integer numberOfPositions;

    @Column
    private String remoteWorkPolicy;

    @Column(columnDefinition = "TEXT")
    private String benefits;

    @ManyToMany
    @JoinTable(
        name = "job_skills",
        joinColumns = @JoinColumn(name = "job_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> requiredSkills = new HashSet<>();

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private Set<JobApplication> applications = new HashSet<>();

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
