package com.jobportal.job_recommendation_system.controller;

import com.jobportal.job_recommendation_system.dto.CompanyDTO;
import com.jobportal.job_recommendation_system.dto.JobDTO;
import com.jobportal.job_recommendation_system.mapper.CompanyMapper;
import com.jobportal.job_recommendation_system.mapper.JobMapper;
import com.jobportal.job_recommendation_system.model.CompanyModel;
import com.jobportal.job_recommendation_system.model.JobModel;
import com.jobportal.job_recommendation_system.service.CompanyService;
import com.jobportal.job_recommendation_system.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/registerCompany")
    public ResponseEntity<Map<String, Object>> saveCompany(@RequestBody CompanyModel company) {
        Map<String, Object> response = new HashMap<>();

        try {
            CompanyModel newCompany = companyService.saveCompany(company);
            emailService.sendEMail(
                    newCompany.getEmail(),
                    "Registration Email",
                    "Thank you " + newCompany.getComp_name() + "\nfor using our app!"
            );
            CompanyDTO companyDTO = CompanyMapper.toDTO(newCompany);
            response.put("success", true);
            response.put("message", "Company saved successfully!");
            response.put("company", companyDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

//  GET /api/company/companyProfile
    @GetMapping("/companyProfile")
    public ResponseEntity<Map<String, Object>> getCompanyByEmail(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        try {
            String companyEmail = authentication.getName();
            CompanyModel company = companyService.getCompanyByUserEmail(companyEmail);
            CompanyDTO companyDTO = CompanyMapper.toDTO(company);
            response.put("success", true);
            response.put("message", "Company found!");
            response.put("company", companyDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    //All companies
    @GetMapping("/allCompanies")
    public ResponseEntity<Map<String, Object>> getAllCompanies() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<CompanyModel> companyList = companyService.getAllCompanies();
            List<CompanyDTO> companyDTOList = CompanyMapper.toDTOList(companyList);
            response.put("success", true);
            response.put("message", "All companies found!");
            response.put("companies", companyDTOList);
            response.put("total_companies", companyList.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/companyjobs")
    public ResponseEntity<Map<String, Object>> getCompanyJobs(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        try {
            String companyEmail = authentication.getName();
            List<JobModel> jobs = companyService.getCompanyJobs(companyEmail);
            List<JobDTO> jobDTOs= JobMapper.toDTOList(jobs);
            response.put("success", true);
            response.put("message", "Company found!");
            response.put("company", companyEmail);
            response.put("jobs", jobDTOs);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/updateCompany")
    public ResponseEntity<Map<String, Object>> updateCompany(Authentication authentication, @RequestBody CompanyModel company) {
        Map<String, Object> response = new HashMap<>();

        try {
            String  companyEmail = authentication.getName();
            CompanyModel existingCompany = companyService.updateCompany(company, companyEmail);
            CompanyDTO companyDTO = CompanyMapper.toDTO(existingCompany);
            response.put("success", true);
            response.put("message", "Company updated successfully!");
            response.put("company", companyDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/delCompany")
    public ResponseEntity<Map<String, Object>> deleteCompany(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        try {
            String companyEmail = authentication.getName();
            companyService.deleteCompany(companyEmail);
            response.put("success", true);
            response.put("message", "Company deleted successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
