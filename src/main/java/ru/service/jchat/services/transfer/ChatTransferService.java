package ru.service.jchat.services.transfer;

import ru.service.jchat.models.entities.ChatEntity;
import ru.service.jchat.models.response.dto.ChatDTO;

import java.util.List;

public interface ChatTransferService {
    ChatDTO chatToDto(ChatEntity chat);
    List<ChatDTO> chatListToDto(List<ChatEntity> chatList);
}
