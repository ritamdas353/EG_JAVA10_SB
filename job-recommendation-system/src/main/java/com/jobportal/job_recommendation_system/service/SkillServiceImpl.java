package com.jobportal.job_recommendation_system.service;

import com.jobportal.job_recommendation_system.model.SkillModel;
import com.jobportal.job_recommendation_system.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SkillServiceImpl implements SkillService{

    @Autowired
    private SkillRepository skillRepository;

    @Override
    @Transactional
    public SkillModel saveSkill(SkillModel skill) {
        // already exists
        if(skillRepository.findBySkillNameIgnoreCase(skill.getSkill_name()).isPresent()){
            throw new RuntimeException("Skill already exists");
        }
        return skillRepository.save(skill);

    }

    @Override
    @Transactional
    public SkillModel updateSkill(SkillModel skill, Long skill_id) {
        SkillModel existingSkill = skillRepository.findById(skill_id).orElseThrow(RuntimeException::new);

        existingSkill.setSkill_name(skill.getSkill_name());

        skillRepository.save(existingSkill);
        return existingSkill;
    }

    @Override
    public List<SkillModel> getAllSkills() {
        return skillRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteSkill(Long skill_id) {
        skillRepository.findById(skill_id).orElseThrow(RuntimeException::new);
        skillRepository.deleteById(skill_id);
    }
}
