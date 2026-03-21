package com.jobportal.job_recommendation_system.repository;

import com.jobportal.job_recommendation_system.model.CompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<CompanyModel, Long> {
    Optional<CompanyModel> findByCompanyEmail(String companyEmail);
    void deleteByCompanyEmail(String companyEmail);
}
