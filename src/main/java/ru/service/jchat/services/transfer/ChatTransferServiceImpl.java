package ru.service.jchat.services.transfer;

import org.springframework.stereotype.Service;
import ru.service.jchat.models.entities.ChatEntity;
import ru.service.jchat.models.response.dto.ChatDTO;
import ru.service.jchat.models.response.dto.MessageDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class ChatTransferServiceImpl implements ChatTransferService {
    private final MessageTransferService messageTransferService;
    private final UserTransferService userTransferService;

    public ChatTransferServiceImpl(MessageTransferService messageTransferService, UserTransferService userTransferService) {
        this.messageTransferService = messageTransferService;
        this.userTransferService = userTransferService;
    }

    public ChatDTO chatToDto(ChatEntity chat) {
        List<MessageDTO> pinnedMessages = new ArrayList<>();

        if (!isNull(chat.getPinnedMessage())) {
            pinnedMessages = chat.getPinnedMessage().stream().map(messageTransferService::messageToDto).collect(Collectors.toList());
        }

        return new ChatDTO(
                chat.getId(),
                chat.getTitle(),
                chat.getImage(),
                chat.getCreated(),
                chat.getUpdated(),
                chat.getChatType(),
                pinnedMessages,
                userTransferService.userListToDto(chat.getUsers()),
                userTransferService.userListToDto(chat.getAdmins())
        );
    }

    public List<ChatDTO> chatListToDto(List<ChatEntity> chats) {
        List<ChatDTO> chatList = new ArrayList<>();

        for (ChatEntity item : chats) {
            List<MessageDTO> pinnedMessages = new ArrayList<>();

            if (!isNull(item.getPinnedMessage())) {
                pinnedMessages = item.getPinnedMessage().stream().map(messageTransferService::messageToDto).collect(Collectors.toList());
            }

            ChatDTO chatDTO = new ChatDTO(
                    item.getId(),
                    item.getTitle(),
                    item.getImage(),
                    item.getCreated(),
                    item.getUpdated(),
                    item.getChatType(),
                    pinnedMessages,
                    userTransferService.userListToDto(item.getUsers()),
                    userTransferService.userListToDto(item.getAdmins())
            );

            chatList.add(chatDTO);
        }

        return chatList;
    }
}
