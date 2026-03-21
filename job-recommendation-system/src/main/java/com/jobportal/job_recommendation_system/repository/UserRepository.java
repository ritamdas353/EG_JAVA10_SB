package com.jobportal.job_recommendation_system.repository;

import com.jobportal.job_recommendation_system.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByUserEmail(String userEmail);
    void deleteByUserEmail(String userEmail);
}
