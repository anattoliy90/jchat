package ru.service.jchat.utils;

import io.jsonwebtoken.Claims;
import ru.service.jchat.jwt.JwtAuthentication;
import ru.service.jchat.models.entities.AuthorityEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class JwtUtils {
    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setAuthority(getAuthority(claims));
        jwtInfoToken.setFirstName(claims.get("firstName", String.class));
        jwtInfoToken.setLastName(claims.get("lastName", String.class));
        jwtInfoToken.setEmail(claims.getSubject());
        return jwtInfoToken;
    }

    private static Set<AuthorityEntity> getAuthority(Claims claims) {
        final List<String> roles = claims.get("authority", List.class);
        return roles.stream()
                .map(AuthorityEntity::valueOf)
                .collect(Collectors.toSet());
    }
}
