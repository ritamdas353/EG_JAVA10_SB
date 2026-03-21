package com.jobportal.job_recommendation_system.controller;

import com.jobportal.job_recommendation_system.dto.ApplicantDTO;
import com.jobportal.job_recommendation_system.dto.ApplicantSkillsDTO;
import com.jobportal.job_recommendation_system.dto.SkillResponseDTO;
import com.jobportal.job_recommendation_system.mapper.ApplicantMapper;
import com.jobportal.job_recommendation_system.model.ApplicantModel;
import com.jobportal.job_recommendation_system.service.ApplicantService;
import com.jobportal.job_recommendation_system.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/applicant")
public class ApplicantController {
    @Autowired
    private ApplicantService applicantService;
    @Autowired
    private EmailService emailService;

//  POST /api/applicant/resgisterApplicant
    @PostMapping("/registerApplicant")
    public ResponseEntity<Map<String, Object>> saveApplicant(@RequestBody ApplicantModel applicant) {
        Map<String,Object> response = new HashMap<>();

        try {
            ApplicantModel details = applicantService.saveApplicant(applicant);
            emailService.sendEMail(
              details.getApplicant_email(),
              "Registration Email",
              "Thank you " + details.getApplicant_name() + "\nfor using our app!"
            );
            ApplicantDTO applicantDTO = ApplicantMapper.toDTO(details);
            response.put("success",true);
            response.put("message","Applicant added successfully");
            response.put("applicant",applicantDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Never Gonna use this LOL
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allApplicants")
    public ResponseEntity<Map<String, Object>> getAllApplicants() {
        Map<String,Object> response = new HashMap<>();
        try {
            List<ApplicantModel> applicants = applicantService.findAllApplicants();

            List<ApplicantDTO> applicantDTOs = applicants.stream()
                    .map(ApplicantMapper::toDTO)
                    .collect(Collectors.toList());
            response.put("success",true);
            response.put("message","success");
            response.put("applicants",applicantDTOs);
            response.put("total_applicants",applicantDTOs.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/applicantProfile")
    public ResponseEntity<Map<String, Object>> getApplicantByEmail(Authentication authentication) {
        // example: /api/applicant/applicantProfile
        Map<String,Object> response = new HashMap<>();
        try {
            String applicantEmail = authentication.getName();
            ApplicantModel applicant = applicantService.getApplicantByUserEmail(applicantEmail);
            ApplicantDTO applicantDTO = ApplicantMapper.toDTO(applicant);
            response.put("success",true);
            response.put("message","success");
            response.put("applicant",applicantDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

//  GET /applicants/match
    @GetMapping("/match")
    public ResponseEntity<Map<String, Object>> findApplicantBySkillMatch(@RequestBody List<String> job_skills) {
        Map<String,Object> response = new HashMap<>();

        try {
            List<ApplicantModel> applicants = applicantService.findApplicantBySkillMatch(job_skills);
            List<ApplicantDTO> applicantDTOs = applicants.stream()
                    .map(ApplicantMapper::toDTO)
                    .toList();
            response.put("success",true);
            response.put("message","success");
            response.put("applicants",applicantDTOs);
            response.put("total_applicants",applicantDTOs.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/updateApplicant")
    public ResponseEntity<Map<String, Object>> updateApplicant(Authentication authentication, @RequestBody ApplicantModel applicant) {
        Map<String,Object> response = new HashMap<>();

        try {
            String applicantEmail = authentication.getName();
            ApplicantModel updatedApplicant = applicantService.updateApplicant(applicantEmail,applicant);
            ApplicantDTO applicantDTO = ApplicantMapper.toDTO(updatedApplicant);
            response.put("success",true);
            response.put("message","Applicant updated successfully!");
            response.put("updated_applicant", applicantDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/updateSkills")
    public ResponseEntity<Map<String, Object>> updateSkill(Authentication authentication, @RequestBody List<Long> skillIds) {
        Map<String,Object> response = new HashMap<>();

        try {
            String applicantEmail = authentication.getName();
            applicantService.updateSkill(applicantEmail,skillIds);
            ApplicantModel updatedApplicant = applicantService.getApplicantByUserEmail(applicantEmail);
            List<SkillResponseDTO> skillDTOs = updatedApplicant.getSkills().stream()
                    .map(skill -> new SkillResponseDTO(skill.getSkill_id(), skill.getSkillName()))
                    .toList();
            ApplicantSkillsDTO applicantSkillsDTO = new ApplicantSkillsDTO(applicantEmail, skillDTOs);
            response.put("success",true);
            response.put("message","Applicant updated successfully!");
            response.put("updated_applicant_skills", applicantSkillsDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/delApplicant")
    public ResponseEntity<Map<String, Object>> deleteApplicantById(Authentication authentication) {
        Map<String,Object> response = new HashMap<>();

        try {
            String applicantEmail = authentication.getName();
            applicantService.deleteApplicantById(applicantEmail);
            response.put("success",true);
            response.put("message","Applicant deleted successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

}
