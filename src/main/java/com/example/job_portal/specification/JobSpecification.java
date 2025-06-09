package com.example.job_portal.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.job_portal.model.Job;

public class JobSpecification {
    public static Specification<Job> hasLocation(String location) {
        return (root, query, criteriaBuilder) -> 
            location == null ? null : criteriaBuilder.equal(root.get("location"), location);
    }

    public static Specification<Job> hasCompany(String company) {
        return (root, query, criteriaBuilder) -> 
            company == null ? null : criteriaBuilder.equal(root.get("company"), company);
    }

    public static Specification<Job> hasEmploymentType(String employmentType) {
        return (root, query, criteriaBuilder) -> 
            employmentType == null ? null : criteriaBuilder.equal(root.get("employmentType"), employmentType);
    }

    public static Specification<Job> hasSalaryGreaterThanOrEqual(Double salary) {
        return (root, query, criteriaBuilder) -> 
            salary == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("salary"), salary);
    }

    public static Specification<Job> hasExperienceLevel(Integer experience) {
        return (root, query, criteriaBuilder) -> 
            experience == null ? null : criteriaBuilder.equal(root.get("experienceLevel"), experience);
    }

    public static Specification<Job> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> 
            title == null ? null : criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }
}
