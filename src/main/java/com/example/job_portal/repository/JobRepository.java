package com.example.job_portal.repository;

import com.example.job_portal.model.Job;
import com.example.job_portal.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByCompanyId(Long companyId);
    List<Job> findByJobStatus(String status);
    List<Job> findByEmploymentType(String employmentType);
    List<Job> findByLocation(String location);
    List<Job> findBySalaryBetween(Double minSalary, Double maxSalary);
    List<Job> findByExperienceLessThanEqual(int experience);
    List<Job> findByApplicationDeadlineBefore(LocalDateTime date);
    
    @Query("SELECT j FROM Job j WHERE j.jobStatus = 'Active' AND j.applicationDeadline > :now")
    List<Job> findActiveJobs(@Param("now") LocalDateTime now);
    
    @Query("SELECT j FROM Job j JOIN j.requiredSkills s WHERE s IN :skills")
    List<Job> findBySkills(@Param("skills") Set<Skill> skills);
    
    @Query("SELECT j FROM Job j WHERE j.title LIKE %:keyword% OR j.description LIKE %:keyword%")
    List<Job> searchJobs(@Param("keyword") String keyword);
}
