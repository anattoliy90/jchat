package ru.service.jchat.models.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserCreateRequest {
    @NotBlank(message = "First name cannot be empty!")
    private String firstName;

    private String lastName;

    @Email(message = "Email is not correct")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "Password cannot be empty!")
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
