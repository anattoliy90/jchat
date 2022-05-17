package ru.service.jchat.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.service.jchat.config.Constants;
import ru.service.jchat.models.request.ChatRequest;
import ru.service.jchat.models.response.dto.ChatDTO;
import ru.service.jchat.services.ChatService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = Constants.CHATS)
@Tag(name="Контроллер для работы с чатами")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
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
        return chatService.add(request);
    }

    @ResponseBody
    @PutMapping(path = "/{id}")
    @Operation(summary = "Изменить чат", security = @SecurityRequirement(name = "jwtAuth"))
    public ChatDTO update(@PathVariable("id") Long id, @Valid @RequestBody ChatRequest request) {
        return chatService.update(id, request);
    }

    @ResponseBody
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
}
