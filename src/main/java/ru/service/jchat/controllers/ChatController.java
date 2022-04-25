package ru.service.jchat.controllers;

import org.springframework.web.bind.annotation.*;
import ru.service.jchat.config.Constants;
import ru.service.jchat.models.request.ChatRequest;
import ru.service.jchat.models.response.dto.ChatDTO;
import ru.service.jchat.services.ChatService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = Constants.CHATS)
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @ResponseBody
    @GetMapping(path = "/{id}")
    public ChatDTO getById(@PathVariable("id") Long id) {
        return chatService.getById(id);
    }

    @ResponseBody
    @PostMapping(path = "/add")
    public ChatDTO add(@Valid @RequestBody ChatRequest request) {
        return chatService.add(request);
    }

    @ResponseBody
    @PutMapping(path = "/{id}")
    public ChatDTO update(@PathVariable("id") Long id, @Valid @RequestBody ChatRequest request) {
        return chatService.update(id, request);
    }

    @ResponseBody
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable("id") Long id) {
        chatService.delete(id);
    }

    @ResponseBody
    @GetMapping(path = "/search/{title}")
    public List<ChatDTO> search(@PathVariable String title) {
        return chatService.search(title);
    }
}
