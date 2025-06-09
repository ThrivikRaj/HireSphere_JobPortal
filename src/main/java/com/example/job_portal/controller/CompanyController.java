package com.example.job_portal.controller;

import com.example.job_portal.dto.CompanyDTO;
import com.example.job_portal.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // Create a new company
    @PostMapping
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO companyDTO) {
        CompanyDTO savedCompany = companyService.registerCompany(companyDTO);
        return ResponseEntity.ok(savedCompany);
    }

    // Get all companies
    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        List<CompanyDTO> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    // Get company by ID
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long id) {
        CompanyDTO company = companyService.getCompanyById(id);
        return ResponseEntity.ok(company);
    }

    // Update a company
    @PutMapping("/{id}")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable Long id, @RequestBody CompanyDTO companyDTO) {
        CompanyDTO updatedCompany = companyService.updateCompany(id, companyDTO);
        return ResponseEntity.ok(updatedCompany);
    }

    // Delete a company
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok().build();
    }
}
