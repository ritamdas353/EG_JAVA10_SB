package com.jobportal.job_recommendation_system.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "skills")
public class SkillModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skill_id;
    @Column(name = "skill_name", nullable = false)
    private String skillName;
    @ManyToMany(mappedBy = "skills")
    private Set<ApplicantModel> applicants;
    @ManyToMany(mappedBy = "skills")
    private Set<JobModel> jobs;

    public SkillModel() {
    }

    public SkillModel(String skillName) {
        this.skillName = skillName;
    }

    public Long getSkill_id() {
        return skill_id;
    }

    public void setSkill_id(Long skill_id) {
        this.skill_id = skill_id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkill_name() {
        return skillName;
    }

    public void setSkill_name(String skill_name) {
        this.skillName = skill_name;
    }

    public Set<ApplicantModel> getApplicants() {
        return applicants;
    }

    public void setApplicants(Set<ApplicantModel> applicants) {
        this.applicants = applicants;
    }

    public Set<JobModel> getJobs() {
        return jobs;
    }

    public void setJobs(Set<JobModel> jobs) {
        this.jobs = jobs;
    }
}
