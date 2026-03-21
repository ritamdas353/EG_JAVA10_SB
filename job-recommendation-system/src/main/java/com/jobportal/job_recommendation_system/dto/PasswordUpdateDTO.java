package com.jobportal.job_recommendation_system.dto;

public class PasswordUpdateDTO {
    private String oldPassword;
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }
    public String getNewPassword() {
        return newPassword;
    }

    public PasswordUpdateDTO() {
    }

    public PasswordUpdateDTO(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
