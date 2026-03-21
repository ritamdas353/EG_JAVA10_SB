package com.jobportal.job_recommendation_system.mapper;

import com.jobportal.job_recommendation_system.dto.CompanyDTO;
import com.jobportal.job_recommendation_system.dto.JobDTO;
import com.jobportal.job_recommendation_system.model.CompanyModel;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyMapper {

    public static CompanyDTO toDTO(CompanyModel company) {
        if (company == null) return null;

        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setCompanyName(company.getComp_name());
        companyDTO.setCompanyEmail(company.getEmail());
        companyDTO.setCompanyDetails(company.getDetails());
        companyDTO.setCompanyLocation(company.getLocation());
        companyDTO.setCompanyWebsite(company.getWebsite());

        // Map jobs using JobMapper
        if (company.getJobs() != null && !company.getJobs().isEmpty()) {
            List<JobDTO> jobDTOs = company.getJobs().stream()
                    .map(JobMapper::toDTO)
                    .collect(Collectors.toList());

            companyDTO.setJobs(jobDTOs);
        }

        return companyDTO;
    }

    public static List<CompanyDTO> toDTOList(List<CompanyModel> companies) {

        if (companies == null) return Collections.emptyList();

        return companies.stream()
                .map(CompanyMapper::toDTO)
                .collect(Collectors.toList());
    }
}
