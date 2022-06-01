package ru.service.jchat.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.service.jchat.config.Constants;
import ru.service.jchat.jwt.JwtAuthentication;
import ru.service.jchat.models.request.ChatRequest;
import ru.service.jchat.models.response.dto.ChatDTO;
import ru.service.jchat.services.AuthService;
import ru.service.jchat.services.ChatService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = Constants.CHATS)
@Tag(name = "Контроллер для работы с чатами")
public class ChatController {
    private final ChatService chatService;
    private final AuthService authService;

    public ChatController(ChatService chatService, AuthService authService) {
        this.chatService = chatService;
        this.authService = authService;
    }

    @ResponseBody
    @GetMapping(path = "/{id}")
    @Operation(summary = "Получить чат по id", security = @SecurityRequirement(name = "jwtAuth"))
    public ChatDTO getById(@PathVariable("id") Long id) {
        return chatService.getById(id);
    }

    @ResponseBody
    @PostMapping(path = "/add")
    @Operation(summary = "Добавить чат", security = @SecurityRequirement(name = "jwtAuth"))
    public ChatDTO add(@Valid @RequestBody ChatRequest request) {
        final JwtAuthentication authInfo = authService.getAuthInfo();

        return chatService.add(request, authInfo);
    }

    @PreAuthorize("@secureValidationService.validateChatAdmin(#id)")
    @ResponseBody
    @PutMapping(path = "/{id}")
    @Operation(summary = "Изменить чат", security = @SecurityRequirement(name = "jwtAuth"))
    public ChatDTO update(@PathVariable("id") Long id, @Valid @RequestBody ChatRequest request) {
        return chatService.update(id, request);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Удалить чат", security = @SecurityRequirement(name = "jwtAuth"))
    public void delete(@PathVariable("id") Long id) {
        chatService.delete(id);
    }

    @ResponseBody
    @GetMapping(path = "/search/{title}")
    @Operation(summary = "Поиск чата по названию", security = @SecurityRequirement(name = "jwtAuth"))
    public List<ChatDTO> search(@PathVariable String title) {
        return chatService.search(title);
    }

    @PostMapping("/join/{id}")
    @Operation(summary = "Присоединиться к чату", security = @SecurityRequirement(name = "jwtAuth"))
    public void join(@PathVariable("id") Long id) throws Exception {
        final JwtAuthentication authInfo = authService.getAuthInfo();

        chatService.join(id, authInfo);
    }

    @PostMapping("/leave/{id}")
    @Operation(summary = "Покинуть к чату", security = @SecurityRequirement(name = "jwtAuth"))
    public void leave(@PathVariable("id") Long id) throws Exception {
        final JwtAuthentication authInfo = authService.getAuthInfo();

        chatService.leave(id, authInfo);
    }

    @PreAuthorize("@secureValidationService.validateChatAdmin(#chatId)")
    @PostMapping(path = "/{chatId}/add/user/{userId}")
    @Operation(summary = "Добавить пользователя в чат", security = @SecurityRequirement(name = "jwtAuth"))
    public void addUserToChat(@PathVariable("chatId") Long chatId, @PathVariable("userId") Long userId) throws Exception {
        chatService.addUserToChat(chatId, userId);
    }

    @PreAuthorize("@secureValidationService.validateChatAdmin(#chatId)")
    @PostMapping(path = "/{chatId}/delete/user/{userId}")
    @Operation(summary = "Удалить пользователя из чата", security = @SecurityRequirement(name = "jwtAuth"))
    public void deleteUserFromChat(@PathVariable("chatId") Long chatId, @PathVariable("userId") Long userId) throws Exception {
        final JwtAuthentication authInfo = authService.getAuthInfo();

        chatService.deleteUserFromChat(chatId, userId, authInfo);
    }

    @PreAuthorize("@secureValidationService.validateChatAdmin(#chatId)")
    @PostMapping(path = "/{chatId}/make/admin/{userId}")
    @Operation(summary = "Сделать пользователя админом", security = @SecurityRequirement(name = "jwtAuth"))
    public void makeUserAdmin(@PathVariable("chatId") Long chatId, @PathVariable("userId") Long userId) throws Exception {
        chatService.makeUserAdmin(chatId, userId);
    }

    @PreAuthorize("@secureValidationService.validateChatAdmin(#chatId)")
    @PostMapping(path = "/{chatId}/make/not/admin/{userId}")
    @Operation(summary = "Сделать пользователя не админом", security = @SecurityRequirement(name = "jwtAuth"))
    public void makeUserNotAdmin(@PathVariable("chatId") Long chatId, @PathVariable("userId") Long userId) throws Exception {
        final JwtAuthentication authInfo = authService.getAuthInfo();

        chatService.makeUserNotAdmin(chatId, userId, authInfo);
    }
}
