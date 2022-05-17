package ru.service.jchat.models.response.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.ZonedDateTime;

@Schema(description = "Сущность пользователя")
public class UserDTO {
    @Schema(description = "Идентификатор")
    private final Long id;

    @Schema(description = "Имя")
    private final String firstName;

    @Schema(description = "Фамилия")
    private final String lastName;

    @Schema(description = "Аватар")
    private final String avatar;

    @Schema(description = "Емейл")
    private final String email;

    @Schema(description = "Активирован")
    private final Boolean confirmed;

    @Schema(description = "Дата создания")
    private final ZonedDateTime created;

    @Schema(description = "Дата обновления")
    private final ZonedDateTime updated;

    public UserDTO(Long id, String firstName, String lastName, String avatar, String email, Boolean confirmed, ZonedDateTime created, ZonedDateTime updated) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
        this.email = email;
        this.confirmed = confirmed;
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
        return confirmed;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }
}
