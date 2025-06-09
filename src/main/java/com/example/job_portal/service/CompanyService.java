package com.example.job_portal.service;

import com.example.job_portal.dto.CompanyDTO;
import com.example.job_portal.model.Company;
import com.example.job_portal.repository.CompanyRepository;
import com.example.job_portal.util.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private EmailService emailService;

    public List<CompanyDTO> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(entityMapper::toCompanyDTO)
                .collect(Collectors.toList());
    }

    public CompanyDTO getCompanyById(Long id) {
        return companyRepository.findById(id)
                .map(entityMapper::toCompanyDTO)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
    }

    @Transactional
    public CompanyDTO registerCompany(CompanyDTO companyDTO) {
        Company company = new Company();
        updateCompanyFromDTO(company, companyDTO);
        
        // Set default values if not provided
        if (company.getCreatedAt() == null) {
            company.setCreatedAt(LocalDateTime.now());
        }
        
        if (company.getUpdatedAt() == null) {
            company.setUpdatedAt(LocalDateTime.now());
        }
        
        // Save the company first
        Company savedCompany = companyRepository.save(company);
        
        // Try to send email notification, but don't fail if email sending fails
        try {
            emailService.sendNewCompanyEmail(savedCompany.getEmail(), savedCompany.getName());
        } catch (Exception e) {
            // Log the error but don't throw it
            System.err.println("Failed to send email notification: " + e.getMessage());
        }
        
        return entityMapper.toCompanyDTO(savedCompany);
    }

    @Transactional
    public CompanyDTO updateCompany(Long id, CompanyDTO companyDTO) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
        
        updateCompanyFromDTO(company, companyDTO);
        company.setUpdatedAt(LocalDateTime.now());
        
        Company updatedCompany = companyRepository.save(company);
        return entityMapper.toCompanyDTO(updatedCompany);
    }

    private void updateCompanyFromDTO(Company company, CompanyDTO companyDTO) {
        company.setName(companyDTO.getName());
        company.setEmail(companyDTO.getEmail());
        company.setPhone(companyDTO.getPhone());
        company.setAddress(companyDTO.getAddress());
        company.setWebsite(companyDTO.getWebsite());
        company.setDescription(companyDTO.getDescription());
        company.setSize(companyDTO.getSize());
        company.setIndustry(companyDTO.getIndustry());
        company.setFoundedYear(companyDTO.getFoundedYear());
        company.setLinkedinUrl(companyDTO.getLinkedinUrl());
        company.setTwitterUrl(companyDTO.getTwitterUrl());
        company.setFacebookUrl(companyDTO.getFacebookUrl());
        company.setLogoUrl(companyDTO.getLogoUrl());
        company.setCompanyCulture(companyDTO.getCompanyCulture());
        company.setBenefits(companyDTO.getBenefits());
    }

    public void deleteCompany(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new RuntimeException("Company not found with id: " + id);
        }
        companyRepository.deleteById(id);
    }

    public CompanyDTO getCompanyByEmail(String email) {
        return companyRepository.findByEmail(email)
                .map(entityMapper::toCompanyDTO)
                .orElseThrow(() -> new RuntimeException("Company not found with email: " + email));
    }

    public CompanyDTO getCompanyByName(String name) {
        return companyRepository.findByName(name)
                .map(entityMapper::toCompanyDTO)
                .orElseThrow(() -> new RuntimeException("Company not found with name: " + name));
    }

    public List<CompanyDTO> getCompaniesByIndustry(String industry) {
        return companyRepository.findByIndustry(industry).stream()
                .map(entityMapper::toCompanyDTO)
                .collect(Collectors.toList());
    }

    public List<CompanyDTO> getCompaniesBySize(String size) {
        return companyRepository.findBySize(size).stream()
                .map(entityMapper::toCompanyDTO)
                .collect(Collectors.toList());
    }

    public List<CompanyDTO> searchCompanies(String keyword) {
        return companyRepository.searchCompanies(keyword).stream()
                .map(entityMapper::toCompanyDTO)
                .collect(Collectors.toList());
    }

    public List<CompanyDTO> getCompaniesFoundedAfter(Integer year) {
        return companyRepository.findCompaniesFoundedAfter(year).stream()
                .map(entityMapper::toCompanyDTO)
                .collect(Collectors.toList());
    }
}
