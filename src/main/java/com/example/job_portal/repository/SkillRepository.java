package com.example.job_portal.repository;

import com.example.job_portal.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<Skill> findByName(String name);
    Optional<Skill> findByNameAndCategory(String name, String category);
} 