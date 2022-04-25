package ru.service.jchat.models.response.dto;

import java.time.ZonedDateTime;

public class UserDTO {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String avatar;
    private final String email;
    private final Boolean isConfirmed;
    private final ZonedDateTime created;
    private final ZonedDateTime updated;

    public UserDTO(Long id, String firstName, String lastName, String avatar, String email, Boolean isConfirmed, ZonedDateTime created, ZonedDateTime updated) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
        this.email = email;
        this.isConfirmed = isConfirmed;
        this.created = created;
        this.updated = updated;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getConfirmed() {
        return isConfirmed;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }
}
