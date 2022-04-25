package ru.service.jchat.models.entities;

import org.springframework.security.core.GrantedAuthority;

public enum AuthorityEntity implements GrantedAuthority {
    ADMIN("ADMIN"),
    USER("USER");

    private final String value;

    AuthorityEntity(String value) {
        this.value = value;
    }

    @Override
    public String getAuthority() {
        return value;
    }
}
