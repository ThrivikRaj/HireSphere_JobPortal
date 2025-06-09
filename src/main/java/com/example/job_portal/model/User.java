package com.example.job_portal.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String role; // "CANDIDATE" or "RECRUITER"

    @Column
    private String currentTitle;

    @Column
    private String professionalSummary;

    @Column
    private Integer yearsOfExperience;

    @Column
    private String preferredJobTypes;

    @Column
    private String preferredLocations;

    @Column
    private Double expectedSalary;

    @ManyToMany
    @JoinTable(
        name = "user_skills",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills = new HashSet<>();
}
