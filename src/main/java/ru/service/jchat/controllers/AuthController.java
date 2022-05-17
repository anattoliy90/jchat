package ru.service.jchat.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.service.jchat.config.Constants;
import ru.service.jchat.jwt.RefreshJwtRequest;
import ru.service.jchat.models.request.JwtRequest;
import ru.service.jchat.models.response.JwtResponse;
import ru.service.jchat.services.AuthService;

import javax.security.auth.message.AuthException;

@RestController
@RequestMapping(path = Constants.AUTH)
@Tag(name="Контроллер для работы с jwt токеном")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Авторизация")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) throws AuthException {
        final JwtResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/newAccessToken")
    @Operation(summary = "Получить новый access токен")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refreshTokens")
    @Operation(summary = "Получить новые access и refresh токены", security = @SecurityRequirement(name = "jwtAuth"))
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtResponse token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }
}
