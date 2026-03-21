package com.jobportal.job_recommendation_system.repository;

import com.jobportal.job_recommendation_system.model.SkillModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<SkillModel, Long> {
    Optional<SkillModel> findBySkillNameIgnoreCase(String skillName);
}
