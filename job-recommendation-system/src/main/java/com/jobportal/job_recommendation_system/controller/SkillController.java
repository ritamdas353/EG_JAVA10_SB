package com.jobportal.job_recommendation_system.controller;

import com.jobportal.job_recommendation_system.dto.SkillResponseDTO;
import com.jobportal.job_recommendation_system.model.SkillModel;
import com.jobportal.job_recommendation_system.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/skill")
public class SkillController {
    @Autowired
    private SkillService skillService;

    @PostMapping("/addSkills")
    public ResponseEntity<Map<String, Object>> saveSkill(@RequestBody SkillModel skill) {
        Map<String, Object> response = new HashMap<>();

        try {
            SkillModel newSkill = skillService.saveSkill(skill);
            SkillResponseDTO skillDto = new SkillResponseDTO(
                    newSkill.getSkill_id(),
                    newSkill.getSkill_name()
            );
            response.put("success", true);
            response.put("message", "Skill saved successfully!");
            response.put("skill", skillDto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/presentSkills")
    public ResponseEntity<Map<String, Object>> getAllSkills() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<SkillModel> skills = skillService.getAllSkills();
            List<SkillResponseDTO> skillDTOs = skills.stream()
                    .map(skill -> new SkillResponseDTO(skill.getSkill_id(), skill.getSkill_name()))
                    .toList();
            response.put("success", true);
            response.put("skills", skillDTOs);
            response.put("total_skills", skills.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("{skill_id}")
    public ResponseEntity<Map<String, Object>> updateSkill(@PathVariable Long skill_id, @RequestBody SkillModel skill) {
        Map<String, Object> response = new HashMap<>();

        try {
            SkillModel existingSkill = skillService.updateSkill(skill, skill_id);
            response.put("success", true);
            response.put("message", "Skill updated successfully!");
            response.put("skill", existingSkill);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("{skill_id}")
    public ResponseEntity<Map<String, Object>> deleteSkill(@PathVariable Long skill_id) {
        Map<String, Object> response = new HashMap<>();
        try {
            skillService.deleteSkill(skill_id);
            response.put("success", true);
            response.put("message", "Skill deleted successfully!");
            return ResponseEntity.ok(response);
        }  catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
