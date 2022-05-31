package ru.service.jchat.services;

import org.springframework.stereotype.Service;
import ru.service.jchat.jwt.JwtAuthentication;
import ru.service.jchat.models.entities.ChatEntity;
import ru.service.jchat.models.entities.UserEntity;
import ru.service.jchat.models.request.ChatRequest;
import ru.service.jchat.models.response.dto.ChatDTO;
import ru.service.jchat.repositories.ChatRepository;
import ru.service.jchat.repositories.UserRepository;
import ru.service.jchat.services.transfer.ChatTransferService;

import javax.persistence.EntityNotFoundException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatTransferService chatTransferService;
    private final UserRepository userRepository;

    public ChatService(ChatRepository chatRepository, ChatTransferService chatTransferService, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.chatTransferService = chatTransferService;
        this.userRepository = userRepository;
    }

    public ChatDTO getById(Long id) {
        ChatEntity chat = chatRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Chat with id " + id + " not found!"));

        return chatTransferService.chatToDto(chat);
    }

    public ChatDTO add(ChatRequest request, JwtAuthentication authInfo) {
        UserEntity user = userRepository.findByEmail(authInfo.getEmail()).orElseThrow(() -> new EntityNotFoundException("User with email " + authInfo.getEmail() + " not found!"));
        ChatEntity chat = new ChatEntity();
        List<UserEntity> users = new ArrayList<>();
        users.add(user);
        chat.setUsers(users);
        chat.setAdmins(users);

        return save(chat, request);
    }

    public ChatDTO update(Long id, ChatRequest request) {
        ChatEntity chat = chatRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Chat with id " + id + " not found"));
        chat.setUpdated(ZonedDateTime.now());

        return save(chat, request);
    }

    private ChatDTO save(ChatEntity chat, ChatRequest request) {
        chat.setTitle(request.getTitle());
        chat.setImage(request.getImage());
        chat.setChatType(request.getChatType());

        return chatTransferService.chatToDto(chatRepository.save(chat));
    }

    public void delete(Long id) {
        chatRepository.deleteById(id);
    }

    public List<ChatDTO> search(String title) {
        List<ChatEntity> chatList = chatRepository.searchByTitle(title);

        return chatTransferService.chatListToDto(chatList);
    }

    public void join(Long id, JwtAuthentication authInfo) throws Exception {
        ChatEntity chat = chatRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Chat with id " + id + " not found"));
        UserEntity user = userRepository.findByEmail(authInfo.getEmail()).orElseThrow(() -> new EntityNotFoundException("User with email " + authInfo.getEmail() + " not found!"));
        List<UserEntity> userList = chat.getUsers();

        if (userList.contains(user)) {
            throw new Exception("User is already in the chat");
        }

        userList.add(user);

        chat.setUsers(userList);

        chatRepository.save(chat);
    }

    public void leave(Long id, JwtAuthentication authInfo) throws Exception {
        ChatEntity chat = chatRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Chat with id " + id + " not found"));
        UserEntity user = userRepository.findByEmail(authInfo.getEmail()).orElseThrow(() -> new EntityNotFoundException("User with email " + authInfo.getEmail() + " not found!"));
        List<UserEntity> userList = chat.getUsers();

        if (!userList.contains(user)) {
            throw new Exception("User is not in the chat");
        }

        userList.remove(user);

        chat.setUsers(userList);

        chatRepository.save(chat);
    }
}
