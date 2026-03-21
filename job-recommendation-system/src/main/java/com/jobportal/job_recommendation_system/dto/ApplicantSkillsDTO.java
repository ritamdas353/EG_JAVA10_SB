package com.jobportal.job_recommendation_system.dto;

import java.util.List;

public class ApplicantSkillsDTO {
    private String user_email;
    private List<SkillResponseDTO> skills;

    public ApplicantSkillsDTO() {
    }

    public ApplicantSkillsDTO(String user_email, List<SkillResponseDTO> skills) {
        this.user_email = user_email;
        this.skills = skills;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public List<SkillResponseDTO> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillResponseDTO> skills) {
        this.skills = skills;
    }
}
