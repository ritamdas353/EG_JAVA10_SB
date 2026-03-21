package com.jobportal.job_recommendation_system.dto;

import java.util.Set;

public class ApplicantDTO {
    private String applicantName;
    private String applicantEmail;
    private Long applicantPhNumber;
    private Long yrsOfExperience;
    private Double applicantCGPA;
    private String applicantHighQual;
    private String applicantAddress;
    private Set<SkillResponseDTO> skills;

    public ApplicantDTO() {
    }

    public ApplicantDTO(String applicantName, String applicantEmail, Long applicantPhNumber, Long yrsOfExperience, Double applicantCGPA, String applicantHighQual, String applicantAddress, Set<SkillResponseDTO> skills) {
        this.applicantName = applicantName;
        this.applicantEmail = applicantEmail;
        this.applicantPhNumber = applicantPhNumber;
        this.yrsOfExperience = yrsOfExperience;
        this.applicantCGPA = applicantCGPA;
        this.applicantHighQual = applicantHighQual;
        this.applicantAddress = applicantAddress;
        this.skills = skills;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantEmail() {
        return applicantEmail;
    }

    public void setApplicantEmail(String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }

    public Long getApplicantPhNumber() {
        return applicantPhNumber;
    }

    public void setApplicantPhNumber(Long applicantPhNumber) {
        this.applicantPhNumber = applicantPhNumber;
    }

    public Long getYrsOfExperience() {
        return yrsOfExperience;
    }

    public void setYrsOfExperience(Long yrsOfExperience) {
        this.yrsOfExperience = yrsOfExperience;
    }

    public Double getApplicantCGPA() {
        return applicantCGPA;
    }

    public void setApplicantCGPA(Double applicantCGPA) {
        this.applicantCGPA = applicantCGPA;
    }

    public String getApplicantHighQual() {
        return applicantHighQual;
    }

    public void setApplicantHighQual(String applicantHighQual) {
        this.applicantHighQual = applicantHighQual;
    }

    public String getApplicantAddress() {
        return applicantAddress;
    }

    public void setApplicantAddress(String applicantAddress) {
        this.applicantAddress = applicantAddress;
    }

    public Set<SkillResponseDTO> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillResponseDTO> skills) {
        this.skills = skills;
    }
}
