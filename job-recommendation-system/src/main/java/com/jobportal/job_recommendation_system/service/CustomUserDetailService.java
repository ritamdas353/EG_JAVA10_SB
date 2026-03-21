package com.jobportal.job_recommendation_system.service;

import com.jobportal.job_recommendation_system.model.UserModel;
import com.jobportal.job_recommendation_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUser_email())
                .password(user.getUser_password())
                .roles(user.getUser_role())
                .build();
    }
}
