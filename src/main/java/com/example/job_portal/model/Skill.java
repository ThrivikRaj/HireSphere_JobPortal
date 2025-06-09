package com.example.job_portal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "skills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String category;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "requiredSkills")
    private Set<Job> jobs = new HashSet<>();

    @ManyToMany(mappedBy = "skills")
    private Set<User> users = new HashSet<>();
} 