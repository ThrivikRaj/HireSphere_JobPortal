package com.example.job_portal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.job_portal.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByEmail(String email);
    Optional<Company> findByName(String name);
    List<Company> findByIndustry(String industry);
    List<Company> findBySize(String size);

    @Query("SELECT c FROM Company c WHERE c.name LIKE %:keyword% OR c.description LIKE %:keyword%")
    List<Company> searchCompanies(@Param("keyword") String keyword);

    @Query("SELECT c FROM Company c WHERE c.foundedYear >= :year")
    List<Company> findCompaniesFoundedAfter(@Param("year") Integer year);
}
