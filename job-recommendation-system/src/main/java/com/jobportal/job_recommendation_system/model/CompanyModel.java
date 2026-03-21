package com.jobportal.job_recommendation_system.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "company")
public class CompanyModel {
    @Column(name = "comp_name", nullable = false)
    private String comp_name;
    @Id
    @Column(name = "company_email", nullable = false)
    private String companyEmail;
    @Column(name = "details")
    private String details;
    @Column(name = "location", nullable = false)
    private String location;
    @Column(name = "website", nullable = false)
    private String website;
    @OneToOne
    @MapsId
    @JoinColumn(name = "company_email")
    private UserModel user;
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobModel> jobs;

    public CompanyModel() {
    }

    public CompanyModel(String comp_name, String details, String location, String website, UserModel user, List<JobModel> jobs) {
        this.comp_name = comp_name;
        this.details = details;
        this.location = location;
        this.website = website;
        this.user = user;
        this.jobs = jobs;
    }

    public String getComp_name() {
        return comp_name;
    }

    public void setComp_name(String comp_name) {
        this.comp_name = comp_name;
    }

    public String getEmail() {
        return companyEmail;
    }

    public void setEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public List<JobModel> getJobs() {
        return jobs;
    }
}
