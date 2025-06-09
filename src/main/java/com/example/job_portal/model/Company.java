package com.example.job_portal.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column
    private String website;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String industry;

    @Column
    private String size;

    @Column
    private String location;

    @Column
    private Integer foundedYear;

    @Column
    private String linkedinUrl;

    @Column
    private String twitterUrl;

    @Column
    private String facebookUrl;

    @Column
    private String logoUrl;

    @Column(columnDefinition = "TEXT")
    private String companyCulture;

    @Column(columnDefinition = "TEXT")
    private String benefits;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private Set<Job> jobs = new HashSet<>();

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
