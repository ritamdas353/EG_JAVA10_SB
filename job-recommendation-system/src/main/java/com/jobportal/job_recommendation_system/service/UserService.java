package com.jobportal.job_recommendation_system.service;

import com.jobportal.job_recommendation_system.model.UserModel;

public interface UserService {
     UserModel saveUser(UserModel user);

     UserModel login(String userEmail, String password);

     UserModel updateUserPassword(String userEmail, String oldPassword, String newPassword);

     void deleteUser(String user_email);
}
