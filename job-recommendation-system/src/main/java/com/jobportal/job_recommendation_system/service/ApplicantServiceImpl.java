package com.jobportal.job_recommendation_system.service;

import com.jobportal.job_recommendation_system.model.ApplicantModel;
import com.jobportal.job_recommendation_system.model.SkillModel;
import com.jobportal.job_recommendation_system.model.UserModel;
import com.jobportal.job_recommendation_system.repository.ApplicantRepository;
import com.jobportal.job_recommendation_system.repository.SkillRepository;
import com.jobportal.job_recommendation_system.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ApplicantServiceImpl implements ApplicantService{

    @Autowired
    private ApplicantRepository applicantRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SkillRepository skillRepository;

    public UserModel getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user_email = auth.getName(); // this is the email from JWT
        return userRepository.findByUserEmail(user_email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    @Transactional
    public ApplicantModel saveApplicant(ApplicantModel applicant) {
        UserModel loggedInUser = getLoggedInUser();
        if (loggedInUser.getApplicant() != null) {
            throw new RuntimeException("User is already applicant");
        }
        if (loggedInUser.getCompany() != null) {
            throw new RuntimeException("User is already company");
        }

        Set<SkillModel> skills = new HashSet<>();

        for (SkillModel s : applicant.getSkills()) {
            SkillModel skill = skillRepository.findById(s.getSkill_id())
                    .orElseThrow();
            skills.add(skill);
        }
        applicant.setSkills(skills);
        loggedInUser.setUser_role("APPLICANT");
        applicant.setUser(loggedInUser);

        return applicantRepository.save(applicant);
    }

    @Override
    @Transactional
    public ApplicantModel updateApplicant(String user_email, ApplicantModel applicant) {
        ApplicantModel existingApplicant = applicantRepository.findByApplicantEmail(user_email).orElseThrow(
                RuntimeException::new
        );

        existingApplicant.setApplicant_name(applicant.getApplicant_name());
        existingApplicant.setApplicant_ph_num(applicant.getApplicant_ph_num());
        existingApplicant.setApplicant_yrs_of_exp(applicant.getApplicant_yrs_of_exp());
        existingApplicant.setApplicant_cgpa(applicant.getApplicant_cgpa());
        existingApplicant.setApplicant_high_qual(applicant.getApplicant_high_qual());
        existingApplicant.setApplicant_address(applicant.getApplicant_address());

        return applicantRepository.save(existingApplicant);
    }

    @Override
    public void updateSkill(String user_email, List<Long> skillIds) {
        ApplicantModel existingApplicant = applicantRepository.findByApplicantEmail(user_email).orElseThrow(
                RuntimeException::new
        );
        Set<SkillModel> skills = new HashSet<>();
        for(Long id : skillIds){
            skillRepository.findById(id).ifPresent(skills::add);
        }
        existingApplicant.getSkills().clear();
        existingApplicant.getSkills().addAll(skills);

        applicantRepository.save(existingApplicant);
    }

    @Override
    public List<ApplicantModel> findAllApplicants() {
        return applicantRepository.findAll();
    }

    @Override
    public ApplicantModel getApplicantByUserEmail(String user_email) {
        return applicantRepository
                .findByApplicantEmail(user_email)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public List<ApplicantModel> findApplicantBySkillMatch(List<String> skills) {
        int totalSkills = skills.size();
        double minPercentage = 0.7;
        Long minMatchCount = (long) Math.floor(totalSkills * minPercentage);

        return applicantRepository.findApplicantsBySkillMatch(skills, minMatchCount);
    }

    @Override
    @Transactional
    public void deleteApplicantById(String user_email) {
        applicantRepository.findByApplicantEmail(user_email).orElseThrow(
                RuntimeException::new
        );
        applicantRepository.deleteByApplicantEmail(user_email);
    }
}
