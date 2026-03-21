package com.jobportal.job_recommendation_system.dto;

import java.util.List;

public class CompanyDTO {
    private String companyName;
    private String companyEmail;
    private String companyDetails;
    private String companyLocation;
    private String companyWebsite;
    private List<JobDTO> jobs;

    public CompanyDTO() {
    }

    public CompanyDTO(String companyName, String companyEmail, String companyDetails, String companyLocation, String companyWebsite, List<JobDTO> jobs) {
        this.companyName = companyName;
        this.companyEmail = companyEmail;
        this.companyDetails = companyDetails;
        this.companyLocation = companyLocation;
        this.companyWebsite = companyWebsite;
        this.jobs = jobs;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyDetails() {
        return companyDetails;
    }

    public void setCompanyDetails(String companyDetails) {
        this.companyDetails = companyDetails;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public List<JobDTO> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobDTO> jobs) {
        this.jobs = jobs;
    }
}
