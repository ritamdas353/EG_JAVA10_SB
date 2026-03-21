package com.jobportal.job_recommendation_system.mapper;

import com.jobportal.job_recommendation_system.dto.JobCompanyDTO;
import com.jobportal.job_recommendation_system.dto.JobDTO;
import com.jobportal.job_recommendation_system.dto.SkillResponseDTO;
import com.jobportal.job_recommendation_system.model.JobModel;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JobMapper {

    public static JobDTO toDTO(JobModel job) {
        return new JobDTO(
                job.getJob_id().toString(),
                job.getTitle(),
                job.getDetails(),
                job.getMin_exp(),
                job.getCompany() != null ? new JobCompanyDTO(
                        job.getCompany().getComp_name(),
                        job.getCompany().getEmail()
                ) : null,
                job.getSkills() != null ? job.getSkills().stream()
                        .map(skill -> new SkillResponseDTO(
                                skill.getSkill_id(),
                                skill.getSkillName()
                        ))
                        .collect(Collectors.toSet())
                        : null
        );
    }

    public static List<JobDTO> toDTOList(List<JobModel> jobs) {
        if (jobs == null) return Collections.emptyList();
        return jobs.stream()
                .map(JobMapper::toDTO)
                .collect(Collectors.toList());
    }
}
