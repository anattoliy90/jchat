package ru.service.jchat.models.request;

import javax.validation.constraints.Size;

public class PasswordChangeRequest {
    private String oldPassword;

    @Size(min = 8, max = 32)
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
