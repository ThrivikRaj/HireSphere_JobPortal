package com.example.job_portal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.job_portal.model.Job;
import com.example.job_portal.repository.JobRepository;
import com.example.job_portal.specification.JobSpecification;
import com.example.job_portal.dto.JobDTO;
import com.example.job_portal.dto.SkillDTO;
import com.example.job_portal.model.Company;
import com.example.job_portal.model.Skill;
import com.example.job_portal.repository.CompanyRepository;
import com.example.job_portal.repository.SkillRepository;
import com.example.job_portal.util.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final SkillRepository skillRepository;
    private final EntityMapper entityMapper;
    private final EmailService emailService;  // Injecting EmailService

    @Autowired
    public JobService(JobRepository jobRepository, 
                     CompanyRepository companyRepository, 
                     SkillRepository skillRepository, 
                     EntityMapper entityMapper, 
                     EmailService emailService) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.skillRepository = skillRepository;
        this.entityMapper = entityMapper;
        this.emailService = emailService;
    }

    public List<JobDTO> getAllJobs() {
        return jobRepository.findAll().stream()
                .map(entityMapper::toJobDTO)
                .collect(Collectors.toList());
    }

    public JobDTO getJobById(Long id) {
        return jobRepository.findById(id)
                .map(entityMapper::toJobDTO)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
    }

    @Transactional
    public JobDTO createJob(JobDTO jobDTO) {
        Job job = new Job();
        updateJobFromDTO(job, jobDTO);
        
        // Set default values if not provided
        if (job.getJobStatus() == null) {
            job.setJobStatus("Active");
        }
        
        if (job.getCreatedAt() == null) {
            job.setCreatedAt(LocalDateTime.now());
        }
        
        if (job.getUpdatedAt() == null) {
            job.setUpdatedAt(LocalDateTime.now());
        }
        
        Job savedJob = jobRepository.save(job);
        
        // Try to send email notification, but don't fail if email sending fails
        try {
            emailService.sendNewJobEmail(savedJob.getRecruiterEmail(), savedJob.getTitle());
        } catch (Exception e) {
            // Log the error but don't throw it
            System.err.println("Failed to send email notification: " + e.getMessage());
        }
        
        return entityMapper.toJobDTO(savedJob);
    }

    @Transactional
    public JobDTO updateJob(Long id, JobDTO jobDTO) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
        
        updateJobFromDTO(job, jobDTO);
        job.setUpdatedAt(LocalDateTime.now());
        
        Job updatedJob = jobRepository.save(job);
        return entityMapper.toJobDTO(updatedJob);
    }

    private void updateJobFromDTO(Job job, JobDTO jobDTO) {
        job.setRecruiterEmail(jobDTO.getRecruiterEmail());
        job.setExperience(jobDTO.getExperience());
        job.setTitle(jobDTO.getTitle());
        
        if (jobDTO.getCompanyId() != null) {
            Company company = companyRepository.findById(jobDTO.getCompanyId())
                    .orElseThrow(() -> new RuntimeException("Company not found with id: " + jobDTO.getCompanyId()));
            job.setCompany(company);
        }
        
        job.setLocation(jobDTO.getLocation());
        job.setDescription(jobDTO.getDescription());
        job.setSalary(jobDTO.getSalary());
        job.setEmploymentType(jobDTO.getEmploymentType());
        job.setJobStatus(jobDTO.getJobStatus());
        job.setApplicationDeadline(jobDTO.getApplicationDeadline());
        job.setNumberOfPositions(jobDTO.getNumberOfPositions());
        job.setRemoteWorkPolicy(jobDTO.getRemoteWorkPolicy());
        job.setBenefits(jobDTO.getBenefits());
        
        // Handle skills
        if (jobDTO.getRequiredSkills() != null) {
            Set<Skill> skills = new HashSet<>();
            for (SkillDTO skillDTO : jobDTO.getRequiredSkills()) {
                if (skillDTO.getId() != null) {
                    Skill skill = skillRepository.findById(skillDTO.getId())
                            .orElseThrow(() -> new RuntimeException("Skill not found with id: " + skillDTO.getId()));
                    skills.add(skill);
                } else if (skillDTO.getName() != null) {
                    // Try to find existing skill by name
                    Skill skill = skillRepository.findByName(skillDTO.getName())
                            .orElseGet(() -> {
                                // Create new skill if not found
                                Skill newSkill = new Skill();
                                newSkill.setName(skillDTO.getName());
                                newSkill.setCategory(skillDTO.getCategory());
                                return skillRepository.save(newSkill);
                            });
                    skills.add(skill);
                }
            }
            job.setRequiredSkills(skills);
        }
    }

    public void deleteJob(Long id) {
        if (!jobRepository.existsById(id)) {
            throw new RuntimeException("Job not found with id: " + id);
        }
        jobRepository.deleteById(id);
    }

    public List<JobDTO> getJobsByCompany(Long companyId) {
        return jobRepository.findByCompanyId(companyId).stream()
                .map(entityMapper::toJobDTO)
                .collect(Collectors.toList());
    }

    public List<JobDTO> getJobsByStatus(String status) {
        return jobRepository.findByJobStatus(status).stream()
                .map(entityMapper::toJobDTO)
                .collect(Collectors.toList());
    }

    public List<JobDTO> getJobsByEmploymentType(String employmentType) {
        return jobRepository.findByEmploymentType(employmentType).stream()
                .map(entityMapper::toJobDTO)
                .collect(Collectors.toList());
    }

    public List<JobDTO> getJobsByLocation(String location) {
        return jobRepository.findByLocation(location).stream()
                .map(entityMapper::toJobDTO)
                .collect(Collectors.toList());
    }

    public List<JobDTO> getJobsBySalaryRange(Double minSalary, Double maxSalary) {
        return jobRepository.findBySalaryBetween(minSalary, maxSalary).stream()
                .map(entityMapper::toJobDTO)
                .collect(Collectors.toList());
    }

    public List<JobDTO> getJobsByExperience(int experience) {
        return jobRepository.findByExperienceLessThanEqual(experience).stream()
                .map(entityMapper::toJobDTO)
                .collect(Collectors.toList());
    }

    public List<JobDTO> getActiveJobs() {
        return jobRepository.findActiveJobs(LocalDateTime.now()).stream()
                .map(entityMapper::toJobDTO)
                .collect(Collectors.toList());
    }

    public List<JobDTO> searchJobs(String keyword) {
        return jobRepository.searchJobs(keyword).stream()
                .map(entityMapper::toJobDTO)
                .collect(Collectors.toList());
    }
}
