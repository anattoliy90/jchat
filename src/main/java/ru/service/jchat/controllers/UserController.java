package ru.service.jchat.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.service.jchat.config.Constants;
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
@Tag(name = "Контроллер для работы с пользователями")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @GetMapping(path = "/{id}")
    @Operation(summary = "Получить пользователя по id", security = @SecurityRequirement(name = "jwtAuth"))
    public UserDTO get(@PathVariable("id") Long id) {
        return userService.get(id);
    }

    @ResponseBody
    @PostMapping(path = Constants.SING_UP)
    @Operation(summary = "Добавление пользователя", security = @SecurityRequirement(name = "jwtAuth"))
    public UserDTO add(@Valid @RequestBody UserCreateRequest request) {
        return userService.add(request);
    }

    @ResponseBody
    @PutMapping(path = "/{id}")
    @Operation(summary = "Изменение пользователя", security = @SecurityRequirement(name = "jwtAuth"))
    public UserDTO update(@PathVariable("id") Long id, @Valid @RequestBody UserUpdateRequest request) {
        return userService.update(id, request);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Удаление пользователя", security = @SecurityRequirement(name = "jwtAuth"))
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    @ResponseBody
    @GetMapping(path = "/find")
    @Operation(summary = "Поиск пользователей по емейлу", security = @SecurityRequirement(name = "jwtAuth"))
    public List<UserDTO> findByEmail(@RequestParam String email) {
        return userService.searchByEmail(email);
    }

    @ResponseBody
    @GetMapping(path = "/search")
    @Operation(summary = "Поиск пользователей по имени и фамилии", security = @SecurityRequirement(name = "jwtAuth"))
    public List<UserDTO> search(@RequestParam String firstName, @RequestParam String lastName) {
        return userService.search(firstName, lastName);
    }

    @ResponseBody
    @PostMapping(path = "/{id}/avatar")
    @Operation(summary = "Добавление аватара", security = @SecurityRequirement(name = "jwtAuth"))
    public String updateAvatar(@PathVariable("id") Long id, @RequestParam MultipartFile file) throws IOException {
        return userService.updateAvatar(id, file);
    }

    @ResponseBody
    @PostMapping(path = "/{id}/change/password")
    @Operation(summary = "Изменение пароля", security = @SecurityRequirement(name = "jwtAuth"))
    public String changePassword(@PathVariable("id") Long id, @Valid @RequestBody PasswordChangeRequest request) throws Exception {
        return userService.changePassword(id, request);
    }

    @PostMapping(Constants.ACTIVATION + "/{code}")
    @Operation(summary = "Активация пользователя", security = @SecurityRequirement(name = "jwtAuth"))
    public String activate(@PathVariable String code) {
        return userService.activate(code);
    }
}
