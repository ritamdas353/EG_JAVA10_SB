package com.jobportal.job_recommendation_system.service;

import com.jobportal.job_recommendation_system.model.CompanyModel;
import com.jobportal.job_recommendation_system.model.JobModel;
import com.jobportal.job_recommendation_system.model.UserModel;
import com.jobportal.job_recommendation_system.repository.CompanyRepository;
import com.jobportal.job_recommendation_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService{

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    public UserModel getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user_email = auth.getName(); // this is the email from JWT
        return userRepository.findByUserEmail(user_email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    @Transactional
    public CompanyModel saveCompany(CompanyModel company) {
        UserModel loggedInUser = getLoggedInUser();
        // Check if already Applicant
        if (loggedInUser.getApplicant() != null) {
            throw new RuntimeException("User is already registered as Applicant");
        }

        // Check if already Company
        if (loggedInUser.getCompany() != null) {
            throw new RuntimeException("User is already registered as Company");
        }

        loggedInUser.setUser_role("COMPANY");
        company.setUser(loggedInUser);
        return companyRepository.save(company);
    }

    @Override
    @Transactional
    public CompanyModel updateCompany(CompanyModel company, String company_email) {
        CompanyModel existingCompany = companyRepository.findByCompanyEmail(company_email).orElseThrow(
                RuntimeException::new
        );

        existingCompany.setComp_name(company.getComp_name());
        existingCompany.setDetails(company.getDetails());
        existingCompany.setLocation(company.getLocation());
        existingCompany.setWebsite(company.getWebsite());

        companyRepository.save(existingCompany);
        return existingCompany;
    }

    @Override
    public List<CompanyModel> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public CompanyModel getCompanyByUserEmail(String company_email) {
        return companyRepository.findByCompanyEmail(company_email).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<JobModel> getCompanyJobs(String email) {
        CompanyModel existingCompany = companyRepository.findByCompanyEmail(email).orElseThrow(RuntimeException::new);
        return existingCompany.getJobs();
    }

    @Override
    @Transactional
    public void deleteCompany(String company_email) {
        companyRepository.findByCompanyEmail(company_email).orElseThrow(
                RuntimeException::new
        );
        companyRepository.deleteByCompanyEmail(company_email);
    }
}
