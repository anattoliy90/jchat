package ru.service.jchat.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.service.jchat.config.Constants;
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

    @Value("${jchat.url}")
    private String url;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserTransferService userTransferService;
    private final RoleRepository roleRepository;
    private final EmailService emailService;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            UserTransferService userTransferService,
            RoleRepository roleRepository,
            EmailService emailService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userTransferService = userTransferService;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
    }

    public UserDTO get(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found!"));

        return userTransferService.userToDto(user);
    }

    public UserDTO add(UserCreateRequest request) {
        String email = request.getEmail().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new DuplicateKeyException("User with email " + email + " already exists");
        }

        UserEntity user = new UserEntity();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setActivationCode(UUID.randomUUID().toString());

        Set<RoleEntity> roles = Set.of(roleRepository.getById(1L));
        user.setRoles(roles);

        userRepository.save(user);

        emailService.sendSimpleMessage(
                email,
                "Activation code",
                "Welcome to jchat. To confirm registration, follow the link " + url + Constants.USERS + Constants.ACTIVATION + "/" + user.getActivationCode()
        );

        return userTransferService.userToDto(user);
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


    public String activate(String code) {
        UserEntity user = userRepository.findByActivationCode(code).orElseThrow(() -> new EntityNotFoundException("Activation code is not found!"));
        user.setConfirmed(true);
        user.setActivationCode(null);
        userRepository.save(user);

        return "User is activated!";
    }
}
