package ru.service.jchat.services;

import org.springframework.stereotype.Service;
import ru.service.jchat.jwt.JwtAuthentication;
import ru.service.jchat.models.entities.ChatEntity;
import ru.service.jchat.models.entities.UserEntity;
import ru.service.jchat.repositories.ChatRepository;
import ru.service.jchat.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class SecureValidationService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    public SecureValidationService(ChatRepository chatRepository, UserRepository userRepository, AuthService authService) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.authService = authService;
    }

    public boolean validateChatAdmin(Long chatId) throws Exception {
        ChatEntity chat = chatRepository.findById(chatId).orElseThrow(() -> new EntityNotFoundException("Chat with id " + chatId + " not found"));
        final JwtAuthentication authInfo = authService.getAuthInfo();
        UserEntity user = userRepository.findByEmail(authInfo.getEmail()).orElseThrow(() -> new EntityNotFoundException("User with email " + authInfo.getEmail() + " not found!"));
        List<UserEntity> userList = chat.getAdmins();

        if (!userList.contains(user)) {
            throw new Exception("User is not an admin");
        }

        return true;
    }
}
