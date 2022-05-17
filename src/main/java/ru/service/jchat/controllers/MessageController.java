package ru.service.jchat.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.service.jchat.config.Constants;
import ru.service.jchat.jwt.JwtAuthentication;
import ru.service.jchat.models.request.MessageCreateRequest;
import ru.service.jchat.models.request.MessagePinRequest;
import ru.service.jchat.models.request.MessageUpdateRequest;
import ru.service.jchat.models.response.dto.MessageDTO;
import ru.service.jchat.services.AuthService;
import ru.service.jchat.services.MessageService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(path = Constants.MESSAGES)
@Tag(name="Контроллер для работы с сообщениями")
public class MessageController {
    private final MessageService messageService;
    private final AuthService authService;

    public MessageController(MessageService messageService, AuthService authService) {
        this.messageService = messageService;
        this.authService = authService;
    }

    @ResponseBody
    @GetMapping(path = "/{id}")
    @Operation(summary = "Получить сообщение по id", security = @SecurityRequirement(name = "jwtAuth"))
    public MessageDTO getById(@PathVariable("id") Long id) {
        return messageService.getById(id);
    }

    @ResponseBody
    @PostMapping(path = "/add")
    @Operation(summary = "Добавить сообщение", security = @SecurityRequirement(name = "jwtAuth"))
    public MessageDTO add(@Valid @RequestBody MessageCreateRequest request) {
        final JwtAuthentication authInfo = authService.getAuthInfo();

        return messageService.add(request, authInfo);
    }

    @ResponseBody
    @PutMapping(path = "/{id}")
    @Operation(summary = "Обновить сообщение", security = @SecurityRequirement(name = "jwtAuth"))
    public MessageDTO update(@PathVariable("id") Long id, @Valid @RequestBody MessageUpdateRequest request) {
        return messageService.update(id, request);
    }

    @ResponseBody
    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Удалить сообщение", security = @SecurityRequirement(name = "jwtAuth"))
    public void delete(@PathVariable("id") Long id) {
        messageService.delete(id);
    }

    @ResponseBody
    @GetMapping(path = "/find/chat/{id}")
    @Operation(summary = "Поиск сообщения в чате", security = @SecurityRequirement(name = "jwtAuth"))
    public Page<MessageDTO> findByChat(@PathVariable Long id, HttpServletRequest request) {
        return messageService.getByChat(id, request.getParameterMap());
    }

    @ResponseBody
    @PostMapping(path = "/{id}/pin")
    @Operation(summary = "Закрепить сообщение", security = @SecurityRequirement(name = "jwtAuth"))
    public MessageDTO pin(@PathVariable Long id, @Valid @RequestBody MessagePinRequest request) {
        return messageService.pin(id, request);
    }
}
