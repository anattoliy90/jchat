package ru.service.jchat.models.request;

import ru.service.jchat.models.entities.ChatTypeEntity;

public class ChatRequest {
    private String title;

    private String image;

    private ChatTypeEntity chatType = ChatTypeEntity.PRIVATE;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ChatTypeEntity getChatType() {
        return chatType;
    }

    public void setChatType(ChatTypeEntity chatType) {
        this.chatType = chatType;
    }
}
