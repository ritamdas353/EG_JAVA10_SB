package com.jobportal.job_recommendation_system.dto;

import java.util.Set;

public class JobDTO {
    private String jobId;
    private String jobTitle;
    private String jobDetails;
    private Double jobMinExp;
    private JobCompanyDTO jobCompanyDTO;
    private Set<SkillResponseDTO> skillDTOs;

    public JobDTO() {
    }

    public JobDTO(String jobId, String jobTitle, String jobDetails, Double jobMinExp, JobCompanyDTO jobCompanyDTO, Set<SkillResponseDTO> skillDTOs) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.jobDetails = jobDetails;
        this.jobMinExp = jobMinExp;
        this.jobCompanyDTO = jobCompanyDTO;
        this.skillDTOs = skillDTOs;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDetails() {
        return jobDetails;
    }

    public void setJobDetails(String jobDetails) {
        this.jobDetails = jobDetails;
    }

    public Double getJobMinExp() {
        return jobMinExp;
    }

    public void setJobMinExp(Double jobMinExp) {
        this.jobMinExp = jobMinExp;
    }

    public JobCompanyDTO getJobCompanyDTO() {
        return jobCompanyDTO;
    }

    public void setJobCompanyDTO(JobCompanyDTO jobCompanyDTO) {
        this.jobCompanyDTO = jobCompanyDTO;
    }

    public Set<SkillResponseDTO> getSkillDTOs() {
        return skillDTOs;
    }

    public void setSkillDTOs(Set<SkillResponseDTO> skillDTOs) {
        this.skillDTOs = skillDTOs;
    }
}
