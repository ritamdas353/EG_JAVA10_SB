package com.jobportal.job_recommendation_system.service;

import com.jobportal.job_recommendation_system.dto.JobSkillsUpdateDTO;
import com.jobportal.job_recommendation_system.model.JobModel;

public interface JobService {
    JobModel saveJob(JobModel jobModel, String company_email);

    JobModel updateJob(JobModel jobModel, Long job_id);

    JobModel updateJobSkills(JobSkillsUpdateDTO jobDTO);

    JobModel getJobById(Long job_id);

    void notifyApplicants(Long jobId, Double minPercentage);

    void deleteJob(Long jobId);
}
