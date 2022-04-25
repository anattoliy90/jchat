package ru.service.jchat.services;

import io.jsonwebtoken.Claims;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.service.jchat.jwt.JwtAuthentication;
import ru.service.jchat.jwt.JwtProvider;
import ru.service.jchat.models.entities.UserEntity;
import ru.service.jchat.models.request.JwtRequest;
import ru.service.jchat.models.response.JwtResponse;

import javax.security.auth.message.AuthException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final Map<String, String> refreshStorage = new HashMap<>();

    public AuthService(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    public JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException {
        final UserEntity user = userService.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new AuthException("User not found"));
        if (user.getPassword().equals(authRequest.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getEmail(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Incorrect password");
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserEntity user = userService.findByEmail(email).orElseThrow(() -> new AuthException("User not found"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserEntity user = userService.findByEmail(email)
                        .orElseThrow(() -> new AuthException("User not found"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getEmail(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Invalid JWT token");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
