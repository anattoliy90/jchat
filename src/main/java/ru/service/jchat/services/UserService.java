package ru.service.jchat.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.service.jchat.models.entities.AuthorityEntity;
import ru.service.jchat.models.entities.RoleEntity;
import ru.service.jchat.models.entities.UserEntity;
import ru.service.jchat.models.request.PasswordChangeRequest;
import ru.service.jchat.models.request.UserCreateRequest;
import ru.service.jchat.models.request.UserUpdateRequest;
import ru.service.jchat.models.response.dto.UserDTO;
import ru.service.jchat.repositories.RoleRepository;
import ru.service.jchat.repositories.UserRepository;
import ru.service.jchat.services.transfer.UserTransferService;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;

@Service
public class UserService {
    @Value("${jchat.upload-path}")
    private String uploadPath;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserTransferService userTransferService;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserTransferService userTransferService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userTransferService = userTransferService;
        this.roleRepository = roleRepository;
    }

    public UserDTO get(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found!"));

        return userTransferService.userToDto(user);
    }

    public UserDTO add(UserCreateRequest request) {
        UserEntity user = new UserEntity();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<RoleEntity> roles = Set.of(roleRepository.getById(1L));
        user.setRoles(roles);

        return userTransferService.userToDto(userRepository.save(user));
    }

    public UserDTO update(Long id, UserUpdateRequest request) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setConfirmed(request.getConfirmed());
        user.setUpdated(ZonedDateTime.now());

        return userTransferService.userToDto(userRepository.save(user));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserDTO> searchByEmail(String email) {
        return userTransferService.userListToDto(userRepository.findByEmailContaining(email));
    }

    public List<UserDTO> search(String firstName, String lastName) {
        return userTransferService.userListToDto(userRepository.searchByFirstAndOrLastName(firstName, lastName));
    }

    public String updateAvatar(Long id, MultipartFile file) throws IOException {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found!"));
        String fileName = "";

        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            File uploadDir = new File(uploadPath);
            Long timestamp = ZonedDateTime.now().toEpochSecond();

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            fileName = timestamp + "_" + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + fileName));
        }

        user.setAvatar(fileName);
        userRepository.save(user);

        return fileName;
    }

    public String changePassword(Long id, PasswordChangeRequest request) throws Exception {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found!"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new Exception("Wrong old password!");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return "The password for user with id " + id + " has been updated";
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
