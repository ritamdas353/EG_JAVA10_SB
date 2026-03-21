package com.jobportal.job_recommendation_system.mapper;

import com.jobportal.job_recommendation_system.dto.ApplicantDTO;
import com.jobportal.job_recommendation_system.dto.SkillResponseDTO;
import com.jobportal.job_recommendation_system.model.ApplicantModel;

import java.util.stream.Collectors;

public class ApplicantMapper {

    public static ApplicantDTO toDTO(ApplicantModel applicant){
        return new ApplicantDTO(
                applicant.getApplicant_name(),
                applicant.getApplicant_email(),
                applicant.getApplicant_ph_num(),
                applicant.getApplicant_yrs_of_exp(),
                applicant.getApplicant_cgpa(),
                applicant.getApplicant_high_qual(),
                applicant.getApplicant_address(),
                applicant.getSkills().stream()
                        .map(skill -> new SkillResponseDTO(
                                skill.getSkill_id(),
                                skill.getSkillName()
                        ))
                        .collect(Collectors.toSet())
        );
    }
}
