package com.jobportal.job_recommendation_system.dto;

import java.util.Set;

public class JobSkillsUpdateDTO {
    private Long jobId;
    private Set<Long> skillIds;

    public JobSkillsUpdateDTO() {
    }

    public JobSkillsUpdateDTO(Long jobId, Set<Long> skillIDs) {
        this.jobId = jobId;
        this.skillIds = skillIDs;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Set<Long> getSkillIds() {
        return skillIds;
    }

    public void setSkillIds(Set<Long> skillIds) {
        this.skillIds = skillIds;
    }
}