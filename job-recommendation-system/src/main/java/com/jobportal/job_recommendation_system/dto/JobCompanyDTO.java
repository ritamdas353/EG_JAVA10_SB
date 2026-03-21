package com.jobportal.job_recommendation_system.dto;

public class JobCompanyDTO {
    private String companyName;
    private String companyEmail;

    public JobCompanyDTO() {
    }

    public JobCompanyDTO(String companyName, String companyEmail) {
        this.companyName = companyName;
        this.companyEmail = companyEmail;
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
}
