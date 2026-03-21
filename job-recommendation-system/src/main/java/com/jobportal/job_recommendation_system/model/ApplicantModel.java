package com.jobportal.job_recommendation_system.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "applicant")
public class ApplicantModel {
    @Column(name = "applicant_name", nullable = false)
    private String applicant_name;
    @Column(name = "applicant_ph_num", nullable = false)
    private Long applicant_ph_num;
    @Id
    @Column(name = "applicant_email", nullable = false)
    private String applicantEmail;
    @Column(name = "applicant_yrs_of_exp")
    private Long applicant_yrs_of_exp;
    @Column(name = "applicant_cgpa")
    private Double applicant_cgpa;
    @Column(name = "applicant_high_qual", nullable = false)
    private String applicant_high_qual;
    @Column(name = "applicant_address", nullable = false)
    private String applicant_address;
    @OneToOne
    @MapsId
    @JoinColumn(name = "applicant_email")
    private UserModel user;
    @ManyToMany
    @JoinTable(
            name = "applicant_skills",
            joinColumns = @JoinColumn(name = "applicant_email"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<SkillModel> skills;

    public ApplicantModel() {
    }

    public ApplicantModel(String applicant_name, Long applicant_ph_num, Long applicant_yrs_of_exp, Double applicant_cgpa, String applicant_high_qual, String applicant_address, UserModel user, Set<SkillModel> skills) {
        this.applicant_name = applicant_name;
        this.applicant_ph_num = applicant_ph_num;
        this.applicant_yrs_of_exp = applicant_yrs_of_exp;
        this.applicant_cgpa = applicant_cgpa;
        this.applicant_high_qual = applicant_high_qual;
        this.applicant_address = applicant_address;
        this.user = user;
        this.skills = skills;
    }

    public String getApplicant_name() {
        return applicant_name;
    }

    public void setApplicant_name(String applicant_name) {
        this.applicant_name = applicant_name;
    }

    public Long getApplicant_ph_num() {
        return applicant_ph_num;
    }

    public void setApplicant_ph_num(Long applicant_ph_num) {
        this.applicant_ph_num = applicant_ph_num;
    }

    public String getApplicant_email() {
        return applicantEmail;
    }

    public void setApplicant_email(String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }

    public Long getApplicant_yrs_of_exp() {
        return applicant_yrs_of_exp;
    }

    public void setApplicant_yrs_of_exp(Long applicant_yrs_of_exp) {
        this.applicant_yrs_of_exp = applicant_yrs_of_exp;
    }

    public Double getApplicant_cgpa() {
        return applicant_cgpa;
    }

    public void setApplicant_cgpa(Double applicant_cgpa) {
        this.applicant_cgpa = applicant_cgpa;
    }

    public String getApplicant_high_qual() {
        return applicant_high_qual;
    }

    public void setApplicant_high_qual(String applicant_high_qual) {
        this.applicant_high_qual = applicant_high_qual;
    }

    public String getApplicant_address() {
        return applicant_address;
    }

    public void setApplicant_address(String applicant_address) {
        this.applicant_address = applicant_address;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public Set<SkillModel> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillModel> skills) {
        this.skills = skills;
    }
}
