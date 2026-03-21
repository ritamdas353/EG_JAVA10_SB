package com.jobportal.job_recommendation_system.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "jobs")
public class JobModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long jobId;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "details")
    private String details;
    @Column(name = "min_exp")
    private Double min_exp;
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyModel company;
    @ManyToMany
    @JoinTable(
            name = "job_skills",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<SkillModel> skills;

    public JobModel() {
    }

    public JobModel(String title, String details, Double min_exp, CompanyModel company, Set<SkillModel> skills) {
        this.title = title;
        this.details = details;
        this.min_exp = min_exp;
        this.company = company;
        this.skills = skills;
    }

    public Long getJob_id() {
        return jobId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Double getMin_exp() {
        return min_exp;
    }

    public void setMin_exp(Double min_exp) {
        this.min_exp = min_exp;
    }

    public CompanyModel getCompany() {
        return company;
    }

    public void setCompany(CompanyModel company) {
        this.company = company;
    }

    public Set<SkillModel> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillModel> skills) {
        this.skills = skills;
    }
}
