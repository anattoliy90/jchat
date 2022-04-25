package ru.service.jchat.services;

import org.springframework.stereotype.Service;
import ru.service.jchat.models.entities.ChatEntity;
import ru.service.jchat.models.request.ChatRequest;
import ru.service.jchat.models.response.dto.ChatDTO;
import ru.service.jchat.repositories.ChatRepository;
import ru.service.jchat.services.transfer.ChatTransferService;

import javax.persistence.EntityNotFoundException;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatTransferService chatTransferService;

    public ChatService(ChatRepository chatRepository, ChatTransferService chatTransferService) {
        this.chatRepository = chatRepository;
        this.chatTransferService = chatTransferService;
    }

    public ChatDTO getById(Long id) {
        ChatEntity chat = chatRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Chat with id " + id + " not found!"));

        return chatTransferService.chatToDto(chat);
    }

    public ChatDTO add(ChatRequest request) {
        ChatEntity chat = new ChatEntity();
//        chat.setUsers();
//        chat.setAdmins();

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
}
