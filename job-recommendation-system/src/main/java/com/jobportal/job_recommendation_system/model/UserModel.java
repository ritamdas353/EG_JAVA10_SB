package com.jobportal.job_recommendation_system.model;


import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class UserModel {
    @Id
    @Column(name = "user_email", nullable = false)
    private String userEmail;
    @Column(name = "user_password", nullable = false)
    private String user_password;
    @Column(name = "user_role")
    private String user_role;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private ApplicantModel applicant;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private CompanyModel company;

    public UserModel() {
    }

    public UserModel(String userEmail, String user_password, String user_role) {
        this.userEmail = userEmail;
        this.user_password = user_password;
        this.user_role = user_role;
    }

    public String getUser_email() {
        return userEmail;
    }

    public void setUser_email(String user_email) {
        this.userEmail = user_email;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public ApplicantModel getApplicant() {
        return applicant;
    }

    public void setApplicant(ApplicantModel applicant) {
        this.applicant = applicant;
    }

    public CompanyModel getCompany() {
        return company;
    }

    public void setCompany(CompanyModel company) {
        this.company = company;
    }
}
