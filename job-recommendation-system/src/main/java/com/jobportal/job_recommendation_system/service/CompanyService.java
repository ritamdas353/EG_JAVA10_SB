package com.jobportal.job_recommendation_system.service;

import com.jobportal.job_recommendation_system.model.CompanyModel;
import com.jobportal.job_recommendation_system.model.JobModel;

import java.util.List;

public interface CompanyService {
    CompanyModel saveCompany(CompanyModel company);

    CompanyModel updateCompany(CompanyModel company, String email);

    List<CompanyModel> getAllCompanies();

    CompanyModel getCompanyByUserEmail(String email);

    List<JobModel> getCompanyJobs(String email);

    void deleteCompany(String email);
}
