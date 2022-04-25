package ru.service.jchat.services.transfer;

import org.springframework.stereotype.Service;
import ru.service.jchat.models.entities.MessageEntity;
import ru.service.jchat.models.response.dto.MessageDTO;

@Service
public class MessageTransferServiceImpl implements MessageTransferService {
    public MessageDTO messageToDto(MessageEntity message) {
        return new MessageDTO(
                message.getId(),
                message.getUser(),
                message.getText(),
                message.getPinned(),
                message.getCreated(),
                message.getUpdated()
        );
    }
}
