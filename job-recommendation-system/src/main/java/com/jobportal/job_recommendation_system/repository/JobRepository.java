package com.jobportal.job_recommendation_system.repository;

import com.jobportal.job_recommendation_system.model.JobModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<JobModel, Long> {
}
