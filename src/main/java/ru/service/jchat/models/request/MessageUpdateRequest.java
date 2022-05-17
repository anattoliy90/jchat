package ru.service.jchat.models.request;

import javax.validation.constraints.NotBlank;

public class MessageUpdateRequest {
    @NotBlank(message = "Message text cannot be empty!")
    private String text;

    private Boolean pinned = false;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getPinned() {
        return pinned;
    }

    public void setPinned(Boolean pinned) {
        pinned = pinned;
    }
}
