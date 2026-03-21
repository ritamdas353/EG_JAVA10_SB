package com.jobportal.job_recommendation_system.repository;

import com.jobportal.job_recommendation_system.model.ApplicantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<ApplicantModel, Long> {
    // CRUD functions

    @Query("""
        SELECT a FROM ApplicantModel a
        JOIN a.skills s
        WHERE s.skillName IN :requiredSkills
        GROUP BY a
        HAVING COUNT(s) >= :minMatchCount""")
    List<ApplicantModel> findApplicantsBySkillMatch(
            @Param("requiredSkills") List<String> requiredSkills,
            @Param("minMatchCount") Long minMatchCount
    );

    Optional<ApplicantModel> findByApplicantEmail(String applicantEmail);
    void deleteByApplicantEmail(String applicantEmail);
}
