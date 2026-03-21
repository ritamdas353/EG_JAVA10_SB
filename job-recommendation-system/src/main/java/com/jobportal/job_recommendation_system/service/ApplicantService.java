package com.jobportal.job_recommendation_system.service;

import com.jobportal.job_recommendation_system.model.ApplicantModel;

import java.util.List;
public interface ApplicantService {
    ApplicantModel saveApplicant(ApplicantModel applicant);

    ApplicantModel updateApplicant(String user_email, ApplicantModel applicant);

    void updateSkill(String user_email, List<Long> skillIds);

    List<ApplicantModel> findAllApplicants();

    ApplicantModel getApplicantByUserEmail(String user_email);

    List<ApplicantModel> findApplicantBySkillMatch(List<String> skills);

    void deleteApplicantById(String user_email);
}
