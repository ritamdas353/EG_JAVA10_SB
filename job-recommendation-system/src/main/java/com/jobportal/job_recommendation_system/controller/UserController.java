package com.jobportal.job_recommendation_system.controller;

import com.jobportal.job_recommendation_system.dto.PasswordUpdateDTO;
import com.jobportal.job_recommendation_system.model.UserModel;
import com.jobportal.job_recommendation_system.service.JWTService;
import com.jobportal.job_recommendation_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTService jwtService;

    @PostMapping("/sign-up")
    public ResponseEntity<Map<String,Object>> saveUser(@RequestBody UserModel user){
        Map<String,Object> response = new HashMap<>();

        try {

            UserModel newUser = userService.saveUser(user);
            response.put("success", true);
            response.put("message", "User saved successfully!");
            response.put("user", newUser);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> login(@RequestBody UserModel user){
        Map<String,Object> response = new HashMap<>();
        try {
            UserModel validUser = userService.login(user.getUser_email(), user.getUser_password());
            if(validUser == null){
                throw  new RuntimeException("Invalid user or password!");
            }
            String token = jwtService.generateToken(validUser.getUser_email());
            response.put("success", true);
            response.put("token", token);
            response.put("user_role", validUser.getUser_role());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/updateUserPassword")
    public ResponseEntity<Map<String,Object>> updateUserPassword(Authentication authentication, @RequestBody PasswordUpdateDTO passwordUpdateDTO){
        Map<String,Object> response = new HashMap<>();

        try {
            String userEmail = authentication.getName();
            UserModel existingUser = userService.updateUserPassword(userEmail,  passwordUpdateDTO.getOldPassword(), passwordUpdateDTO.getNewPassword());
            response.put("success", true);
            response.put("message", "Password updated successfully!");
            response.put("user", existingUser);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/delUser")
    public ResponseEntity<Map<String,Object>> deleteUser(Authentication authentication){
        Map<String,Object> response = new HashMap<>();

        try {
            String userEmail = authentication.getName();
            userService.deleteUser(userEmail);
            response.put("success", true);
            response.put("message", "User deleted successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
