package com.example.job_portal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String website;
    private String description;
    private String size;
    private String location;
    private String industry;
    private Integer foundedYear;
    private String linkedinUrl;
    private String twitterUrl;
    private String facebookUrl;
    private String logoUrl;
    private String companyCulture;
    private String benefits;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
