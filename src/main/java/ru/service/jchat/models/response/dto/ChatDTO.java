package ru.service.jchat.models.response.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ru.service.jchat.models.entities.ChatTypeEntity;

import java.time.ZonedDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatDTO {
    private final Long id;
    private final String title;
    private final String image;
    private final ZonedDateTime created;
    private final ZonedDateTime updated;
    private final ChatTypeEntity chatType;
    private final List<MessageDTO> pinnedMessage;
    private final List<UserDTO> users;
    private final List<UserDTO> admins;

    public ChatDTO(Long id, String title, String image, ZonedDateTime created, ZonedDateTime updated, ChatTypeEntity chatType, List<MessageDTO> pinnedMessage, List<UserDTO> users, List<UserDTO> admins) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.created = created;
        this.updated = updated;
        this.chatType = chatType;
        this.pinnedMessage = pinnedMessage;
        this.users = users;
        this.admins = admins;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public ChatTypeEntity getChatType() {
        return chatType;
    }

    public List<MessageDTO> getPinnedMessage() {
        return pinnedMessage;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public List<UserDTO> getAdmins() {
        return admins;
    }
}
