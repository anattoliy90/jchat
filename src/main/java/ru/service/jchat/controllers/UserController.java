package ru.service.jchat.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.service.jchat.config.Constants;
import ru.service.jchat.jwt.JwtAuthentication;
import ru.service.jchat.models.request.PasswordChangeRequest;
import ru.service.jchat.models.request.UserCreateRequest;
import ru.service.jchat.models.request.UserUpdateRequest;
import ru.service.jchat.models.response.dto.UserDTO;
import ru.service.jchat.services.UserService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = Constants.USERS)
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @GetMapping(path = "/{id}")
    public UserDTO get(@PathVariable("id") Long id) {
        return userService.get(id);
    }

    @ResponseBody
    @PostMapping(path = "/add")
    public UserDTO add(@Valid @RequestBody UserCreateRequest request) {
        return userService.add(request);
    }

    @ResponseBody
    @PutMapping(path = "/{id}")
    public UserDTO update(@PathVariable("id") Long id, @Valid @RequestBody UserUpdateRequest request) {
        return userService.update(id, request);
    }

    @ResponseBody
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    @ResponseBody
    @GetMapping(path = "/find")
    public List<UserDTO> findByEmail(@RequestParam String email) {
        return userService.searchByEmail(email);
    }

    @ResponseBody
    @GetMapping(path = "/search")
    public List<UserDTO> search(@RequestParam String firstName, @RequestParam String lastName) {
        return userService.search(firstName, lastName);
    }

    @ResponseBody
    @PostMapping(path = "/{id}/avatar")
    public String updateAvatar(@PathVariable("id") Long id, @RequestParam MultipartFile file) throws IOException {
        return userService.updateAvatar(id, file);
    }

    @ResponseBody
    @PostMapping(path = "/{id}/change/password")
    public String changePassword(@PathVariable("id") Long id, @Valid @RequestBody PasswordChangeRequest request) throws Exception {
        return userService.changePassword(id, request);
    }
}
