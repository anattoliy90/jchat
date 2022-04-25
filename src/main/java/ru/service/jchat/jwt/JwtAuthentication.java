package ru.service.jchat.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import ru.service.jchat.models.entities.AuthorityEntity;

import java.util.Collection;
import java.util.Set;

public class JwtAuthentication implements Authentication {
    private boolean authenticated;
    private String email;
    private String firstName;
    private String lastName;
    private Set<AuthorityEntity> authority;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public Set<AuthorityEntity> getAuthority() {
        return authority;
    }

    public void setAuthority(Set<AuthorityEntity> authority) {
        this.authority = authority;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authority;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return email;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return firstName + " " + lastName;
    }
}
