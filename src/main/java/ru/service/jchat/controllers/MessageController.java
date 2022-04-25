package ru.service.jchat.controllers;

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
public class MessageController {
    private final MessageService messageService;
    private final AuthService authService;

    public MessageController(MessageService messageService, AuthService authService) {
        this.messageService = messageService;
        this.authService = authService;
    }

    @ResponseBody
    @GetMapping(path = "/{id}")
    public MessageDTO getById(@PathVariable("id") Long id) {
        return messageService.getById(id);
    }

    @ResponseBody
    @PostMapping(path = "/add")
    public MessageDTO add(@Valid @RequestBody MessageCreateRequest request) {
        final JwtAuthentication authInfo = authService.getAuthInfo();

        return messageService.add(request, authInfo);
    }

    @ResponseBody
    @PutMapping(path = "/{id}")
    public MessageDTO update(@PathVariable("id") Long id, @Valid @RequestBody MessageUpdateRequest request) {
        return messageService.update(id, request);
    }

    @ResponseBody
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable("id") Long id) {
        messageService.delete(id);
    }

    @ResponseBody
    @GetMapping(path = "/find/chat/{id}")
    public Page<MessageDTO> findByChat(@PathVariable Long id, HttpServletRequest request) {
        return messageService.getByChat(id, request.getParameterMap());
    }

    @ResponseBody
    @PostMapping(path = "/{id}/pin")
    public MessageDTO pin(@PathVariable Long id, @Valid @RequestBody MessagePinRequest request) {
        return messageService.pin(id, request);
    }




    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/hello/user")
    public ResponseEntity<String> helloUser() {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        return ResponseEntity.ok("Hello user " + authInfo.getPrincipal() + "!");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/hello/admin")
    public ResponseEntity<String> helloAdmin() {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        return ResponseEntity.ok("Hello admin " + authInfo.getPrincipal() + "!");
    }
}
