package com.jobportal.job_recommendation_system.controller;

import com.jobportal.job_recommendation_system.dto.JobDTO;
import com.jobportal.job_recommendation_system.dto.JobSkillsUpdateDTO;
import com.jobportal.job_recommendation_system.mapper.JobMapper;
import com.jobportal.job_recommendation_system.model.JobModel;
import com.jobportal.job_recommendation_system.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/job")
public class JobController {
    @Autowired
    private JobService jobService;

    @PostMapping("/addJob")
    public ResponseEntity<Map<String, Object>> saveJob(@RequestBody JobModel job, Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        try {
            String companyEmail = authentication.getName();
            JobModel newJob = jobService.saveJob(job, companyEmail);
            JobDTO jobDTO = JobMapper.toDTO(newJob);
            response.put("success", true);
            response.put("message", "Job saved successfully!");
            response.put("job", jobDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("{job_id}")
    public ResponseEntity<Map<String, Object>> updateJob(@RequestBody JobModel job, @PathVariable Long job_id) {
        Map<String, Object> response = new HashMap<>();
        try {
            JobModel updatedJob = jobService.updateJob(job, job_id);
            JobDTO jobDTO = JobMapper.toDTO(updatedJob);
            response.put("success", true);
            response.put("message", "Job updated successfully!");
            response.put("job", jobDTO);
            return ResponseEntity.ok(response);
        }  catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/notify/{jobId}/{minPercentage}")
    public ResponseEntity<String> notifyApplicants(@PathVariable("jobId") Long jobId, @PathVariable("minPercentage") Double minPercentage) {

        jobService.notifyApplicants(jobId, minPercentage);

        return ResponseEntity.ok("Emails sent to qualified applicants");
    }

    @PutMapping("/updateJobSkills")
    public ResponseEntity<Map<String, Object>> updateJobSkills(@RequestBody JobSkillsUpdateDTO jobSkillDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            JobModel updatedJobSkills = jobService.updateJobSkills(jobSkillDTO);
            JobDTO jobDTO = JobMapper.toDTO(updatedJobSkills);
            response.put("success", true);
            response.put("message", "Job Skills updated successfully!");
            response.put("job", jobDTO); // !!!change this for JobDTO plz
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/deleteJob/{jobId}")
    public ResponseEntity<Map<String, Object>> deleteJob(@PathVariable("jobId") Long jobId) {
        Map<String, Object> response = new HashMap<>();
        try {
            jobService.deleteJob(jobId);
            response.put("success", true);
            response.put("message", "Job deleted successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
