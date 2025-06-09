package com.example.job_portal.util;

import com.example.job_portal.dto.*;
import com.example.job_portal.model.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EntityMapper {

    public SkillDTO toSkillDTO(Skill skill) {
        if (skill == null) return null;
        
        SkillDTO dto = new SkillDTO();
        dto.setId(skill.getId());
        dto.setName(skill.getName());
        return dto;
    }

    public Skill toSkill(SkillDTO skillDTO) {
        if (skillDTO == null) return null;
        
        Skill skill = new Skill();
        skill.setName(skillDTO.getName());
        return skill;
    }

    public JobDTO toJobDTO(Job job) {
        if (job == null) return null;
        JobDTO dto = new JobDTO();
        dto.setId(job.getId());
        dto.setRecruiterEmail(job.getRecruiterEmail());
        dto.setExperience(job.getExperience());
        dto.setTitle(job.getTitle());
        dto.setCompanyId(job.getCompany().getId());
        dto.setCompanyName(job.getCompany().getName());
        dto.setLocation(job.getLocation());
        dto.setDescription(job.getDescription());
        dto.setSalary(job.getSalary());
        dto.setEmploymentType(job.getEmploymentType());
        dto.setJobStatus(job.getJobStatus());
        dto.setApplicationDeadline(job.getApplicationDeadline());
        dto.setNumberOfPositions(job.getNumberOfPositions());
        dto.setRemoteWorkPolicy(job.getRemoteWorkPolicy());
        dto.setBenefits(job.getBenefits());
        dto.setRequiredSkills(job.getRequiredSkills().stream()
                .map(this::toSkillDTO)
                .collect(Collectors.toSet()));
        dto.setCreatedAt(job.getCreatedAt());
        dto.setUpdatedAt(job.getUpdatedAt());
        return dto;
    }

    public Job toJob(JobDTO dto) {
        if (dto == null) return null;
        Job job = new Job();
        job.setId(dto.getId());
        job.setRecruiterEmail(dto.getRecruiterEmail());
        job.setExperience(dto.getExperience());
        job.setTitle(dto.getTitle());
        job.setLocation(dto.getLocation());
        job.setDescription(dto.getDescription());
        job.setSalary(dto.getSalary());
        job.setEmploymentType(dto.getEmploymentType());
        job.setJobStatus(dto.getJobStatus());
        job.setApplicationDeadline(dto.getApplicationDeadline());
        job.setNumberOfPositions(dto.getNumberOfPositions());
        job.setRemoteWorkPolicy(dto.getRemoteWorkPolicy());
        job.setBenefits(dto.getBenefits());
        return job;
    }

    public CompanyDTO toCompanyDTO(Company company) {
        if (company == null) return null;
        CompanyDTO dto = new CompanyDTO();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setDescription(company.getDescription());
        dto.setWebsite(company.getWebsite());
        dto.setIndustry(company.getIndustry());
        dto.setSize(company.getSize());
        dto.setFoundedYear(company.getFoundedYear());
        dto.setLocation(company.getLocation());
        dto.setEmail(company.getEmail());
        dto.setPhone(company.getPhone());
        dto.setCreatedAt(company.getCreatedAt());
        dto.setUpdatedAt(company.getUpdatedAt());
        return dto;
    }

    public Company toCompany(CompanyDTO dto) {
        if (dto == null) return null;
        Company company = new Company();
        company.setId(dto.getId());
        company.setName(dto.getName());
        company.setDescription(dto.getDescription());
        company.setWebsite(dto.getWebsite());
        company.setIndustry(dto.getIndustry());
        company.setSize(dto.getSize());
        company.setFoundedYear(dto.getFoundedYear());
        company.setLocation(dto.getLocation());
        company.setEmail(dto.getEmail());
        company.setPhone(dto.getPhone());
        return company;
    }

    public UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }
        
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setCurrentTitle(user.getCurrentTitle());
        dto.setProfessionalSummary(user.getProfessionalSummary());
        dto.setYearsOfExperience(user.getYearsOfExperience());
        dto.setPreferredJobTypes(user.getPreferredJobTypes());
        dto.setPreferredLocations(user.getPreferredLocations());
        dto.setExpectedSalary(user.getExpectedSalary());
        
        return dto;
    }

    public User toUser(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setCurrentTitle(dto.getCurrentTitle());
        user.setProfessionalSummary(dto.getProfessionalSummary());
        user.setYearsOfExperience(dto.getYearsOfExperience());
        user.setPreferredJobTypes(dto.getPreferredJobTypes());
        user.setPreferredLocations(dto.getPreferredLocations());
        user.setExpectedSalary(dto.getExpectedSalary());
        
        return user;
    }

    public JobApplicationDTO toJobApplicationDTO(JobApplication application) {
        if (application == null) return null;
        JobApplicationDTO dto = new JobApplicationDTO();
        dto.setId(application.getId());
        dto.setUserId(application.getUser().getId());
        dto.setUserName(application.getUser().getName());
        dto.setJobId(application.getJob().getId());
        dto.setJobTitle(application.getJob().getTitle());
        dto.setStatus(application.getStatus().name());
        dto.setCoverLetter(application.getCoverLetter());
        dto.setApplicationDate(application.getApplicationDate());
        dto.setLastUpdated(application.getLastUpdated());
        dto.setInterviewDate(application.getInterviewDate());
        dto.setInterviewType(application.getInterviewType());
        dto.setInterviewLocation(application.getInterviewLocation());
        dto.setInterviewNotes(application.getInterviewNotes());
        dto.setRejectionReason(application.getRejectionReason());
        dto.setCreatedAt(application.getCreatedAt());
        dto.setUpdatedAt(application.getUpdatedAt());
        return dto;
    }

    public JobApplication toJobApplication(JobApplicationDTO dto) {
        if (dto == null) return null;
        JobApplication application = new JobApplication();
        application.setId(dto.getId());
        application.setCoverLetter(dto.getCoverLetter());
        application.setStatus(JobApplication.ApplicationStatus.valueOf(dto.getStatus()));
        application.setInterviewDate(dto.getInterviewDate());
        application.setInterviewType(dto.getInterviewType());
        application.setInterviewLocation(dto.getInterviewLocation());
        application.setInterviewNotes(dto.getInterviewNotes());
        application.setRejectionReason(dto.getRejectionReason());
        return application;
    }
}
