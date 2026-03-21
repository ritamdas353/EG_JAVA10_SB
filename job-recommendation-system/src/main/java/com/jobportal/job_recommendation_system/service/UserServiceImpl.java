package com.jobportal.job_recommendation_system.service;

import com.jobportal.job_recommendation_system.model.JobModel;
import com.jobportal.job_recommendation_system.model.UserModel;
import com.jobportal.job_recommendation_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserModel saveUser(UserModel user) {
        if(userRepository.findByUserEmail(user.getUser_email()).isPresent()){
            throw new RuntimeException("User already exists!");
        }
        String encryptedPassword = passwordEncoder.encode(user.getUser_password());
        user.setUser_password(encryptedPassword);
        return userRepository.save(user);
    }

    @Override
    public UserModel login(String userEmail, String password) {
        UserModel user = userRepository
                .findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(password, user.getUser_password())){
            throw new RuntimeException("Invalid password");
        }

        return user;
    }

    @Override
    @Transactional
    public UserModel updateUserPassword(String userEmail, String oldPassword, String newPassword) {
        UserModel existingUser = userRepository.findByUserEmail(userEmail).orElseThrow(RuntimeException::new);

        if(!passwordEncoder.matches(oldPassword, existingUser.getUser_password())){
            throw new RuntimeException("Invalid old password");
        }
        String encryptedPassword = passwordEncoder.encode(newPassword);

        existingUser.setUser_password(encryptedPassword);

        userRepository.save(existingUser);

        return existingUser;
    }

    @Override
    @Transactional
    public void deleteUser(String user_email) {
        UserModel user = userRepository.findByUserEmail(user_email).orElseThrow(RuntimeException::new);

        // Clear ManyToMany join tables
        if (user.getApplicant() != null) {
            user.getApplicant().getSkills().clear();
        }
        if (user.getCompany() != null) {
            for (JobModel job : user.getCompany().getJobs()) {
                job.getSkills().clear();
            }
        }

        userRepository.deleteByUserEmail(user_email);
    }
}
