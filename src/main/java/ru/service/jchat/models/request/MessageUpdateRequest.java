package ru.service.jchat.models.request;

import javax.validation.constraints.NotBlank;

public class MessageUpdateRequest {
    @NotBlank(message = "Message text cannot be empty!")
    private String text;

    private Boolean isPinned = false;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getPinned() {
        return isPinned;
    }

    public void setPinned(Boolean pinned) {
        isPinned = pinned;
    }
}
