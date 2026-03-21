package com.jobportal.job_recommendation_system.service;

import com.jobportal.job_recommendation_system.model.SkillModel;

import java.util.List;

public interface SkillService {
    SkillModel saveSkill(SkillModel skill);

    SkillModel updateSkill(SkillModel skill, Long skill_id);

    List<SkillModel> getAllSkills();

    void deleteSkill(Long skill_id);
}
