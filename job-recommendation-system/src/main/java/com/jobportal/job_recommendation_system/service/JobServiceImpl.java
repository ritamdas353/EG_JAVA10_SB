package com.jobportal.job_recommendation_system.service;

import com.jobportal.job_recommendation_system.dto.JobSkillsUpdateDTO;
import com.jobportal.job_recommendation_system.model.*;
import com.jobportal.job_recommendation_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private ApplicantRepository applicantRepository;
    @Autowired
    private EmailService  emailService;
    @Autowired
    private UserRepository userRepository;

    public UserModel getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user_email = auth.getName(); // this is the email from JWT
        return userRepository.findByUserEmail(user_email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    @Transactional
    public JobModel saveJob(JobModel job, String company_email) {
        CompanyModel company = companyRepository.findByCompanyEmail(company_email).orElseThrow(
                ()-> new RuntimeException("Company not found")
        );

        job.setCompany(company);

        return jobRepository.save(job);
    }

    @Override
    @Transactional
    public JobModel updateJob(JobModel job, Long job_id) {
        JobModel existingJob = jobRepository.findById(job_id).orElseThrow(RuntimeException::new);
        existingJob.setTitle(job.getTitle());
        existingJob.setDetails(job.getDetails());
        existingJob.setMin_exp(job.getMin_exp());
        existingJob.setSkills(job.getSkills());
        jobRepository.save(existingJob);
        return existingJob;
    }

    @Override
    public JobModel updateJobSkills(JobSkillsUpdateDTO jobDTO) {
        JobModel existingJob = jobRepository.findById(jobDTO.getJobId()).orElseThrow(RuntimeException::new);
        Set<SkillModel> newSkills = new HashSet<>();
        for(Long id : jobDTO.getSkillIds()){
            skillRepository.findById(id).ifPresent(newSkills::add);
        }
        existingJob.setSkills(newSkills);
        jobRepository.save(existingJob);
        return existingJob;
    }

    @Override
    public JobModel getJobById(Long jobId) {
        return jobRepository.findById(jobId).orElseThrow(RuntimeException::new);
    }

    @Override
    public void notifyApplicants(Long jobId, Double minPercentage) {
        UserModel loggedInUser = getLoggedInUser();

        JobModel job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getCompany().getUser().getUser_email().equals(loggedInUser.getUser_email())) {
            throw new RuntimeException("You are not authorized to notify for this job");
        }

        List<String> requiredSkills = job.getSkills()
                                        .stream()
                                        .map(SkillModel::getSkill_name)
                                        .toList();

        int totalSkills = requiredSkills.size();

        List<ApplicantModel> applicants;
        if (minPercentage > 0) {
            Long minMatchCount = (long) Math.floor(totalSkills * minPercentage);
            applicants =
                    applicantRepository.findApplicantsBySkillMatch(requiredSkills, minMatchCount);
        } else {
            applicants = applicantRepository.findAll();
        }
        for (ApplicantModel applicant : applicants) {

            String email = applicant.getUser().getUser_email();

            String subject = "Interview Opportunity: " + job.getTitle();

            String text =
                    "You have been shortlisted for a job.\n\n" +
                            "Company: " + job.getCompany().getComp_name() + "\n" +
                            "Company Website: " + job.getCompany().getWebsite() + "\n" +
                            "Role: " + job.getTitle() + "\n" +
                            "Description: " + job.getDetails() + "\n" +
                            "Minimum Experience: " + job.getMin_exp() + " years\n\n" +
                            "Company Email: " + job.getCompany().getEmail() + "\n" +
                            "Company Location: " + job.getCompany().getLocation() + "\n\n\n" +
                            "Please apply if interested.";

            emailService.sendEMail(email, subject, text);
        }
    }

    @Override
    @Transactional
    public void deleteJob(Long jobId) {
        jobRepository.findById(jobId).orElseThrow(RuntimeException::new);
        jobRepository.deleteById(jobId);
    }
}
