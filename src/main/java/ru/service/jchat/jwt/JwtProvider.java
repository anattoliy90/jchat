package ru.service.jchat.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import ru.service.jchat.models.entities.RoleEntity;
import ru.service.jchat.models.entities.UserEntity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    private final String jwtAccessSecret;
    private final String jwtRefreshSecret;
    private final Long accessTokenLifeTime;
    private final Long refreshTokenLifeTime;

    public JwtProvider(
            @Value("${jchat.token.access.secret}") String jwtAccessSecret,
            @Value("${jchat.token.refresh.secret}") String jwtRefreshSecret,
            @Value("${jchat.token.access.lifetime}") Long accessTokenLifeTime,
            @Value("${jchat.token.refresh.lifetime}") Long refreshTokenLifeTime
    ) {
        this.jwtAccessSecret = jwtAccessSecret;
        this.jwtRefreshSecret = jwtRefreshSecret;
        this.accessTokenLifeTime = accessTokenLifeTime;
        this.refreshTokenLifeTime = refreshTokenLifeTime;
    }

    public String generateAccessToken(@NonNull UserEntity user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusSeconds(accessTokenLifeTime).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(accessExpiration)
                .signWith(SignatureAlgorithm.HS512, jwtAccessSecret)
                .claim("authority", user.getRoles().stream().map(RoleEntity::getAuthority).collect(Collectors.toSet()))
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .compact();
    }

    public String generateRefreshToken(@NonNull UserEntity user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusSeconds(refreshTokenLifeTime).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(refreshExpiration)
                .signWith(SignatureAlgorithm.HS512, jwtRefreshSecret)
                .compact();
    }

    public boolean validateAccessToken(@NonNull String token) {
        return validateToken(token, jwtAccessSecret);
    }

    public boolean validateRefreshToken(@NonNull String token) {
        return validateToken(token, jwtRefreshSecret);
    }

    private boolean validateToken(@NonNull String token, @NonNull String secret) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            System.out.println("Token expired " + expEx);
        } catch (UnsupportedJwtException unsEx) {
            System.out.println("Unsupported jwt " + unsEx);
        } catch (MalformedJwtException mjEx) {
            System.out.println("Malformed jwt " + mjEx);
        } catch (SignatureException sEx) {
            System.out.println("Invalid signature " + sEx);
        } catch (Exception e) {
            System.out.println("invalid token " + e);
        }
        return false;
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, jwtAccessSecret);
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, jwtRefreshSecret);
    }

    private Claims getClaims(@NonNull String token, @NonNull String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
