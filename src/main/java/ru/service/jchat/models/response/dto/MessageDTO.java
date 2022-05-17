package ru.service.jchat.models.response.dto;

import ru.service.jchat.models.entities.UserEntity;

import java.time.ZonedDateTime;

public class MessageDTO {
    private final Long id;
    private final UserEntity user;
    private final String text;
    private final Boolean pinned;
    private final ZonedDateTime created;
    private final ZonedDateTime updated;

    public MessageDTO(Long id, UserEntity user, String text, Boolean pinned, ZonedDateTime created, ZonedDateTime updated) {
        this.id = id;
        this.user = user;
        this.text = text;
        this.pinned = pinned;
        this.created = created;
        this.updated = updated;
    }

    public Long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public Boolean getPinned() {
        return pinned;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }
}
