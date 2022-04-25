package ru.service.jchat.models.request;

import ru.service.jchat.models.entities.ChatEntity;
import ru.service.jchat.models.entities.UserEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MessageCreateRequest {
    @NotNull(message = "Chat cannot be empty!")
    private ChatEntity chat;

    @NotBlank(message = "Message text cannot be empty!")
    private String text;

    public ChatEntity getChat() {
        return chat;
    }

    public void setChat(ChatEntity chat) {
        this.chat = chat;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
