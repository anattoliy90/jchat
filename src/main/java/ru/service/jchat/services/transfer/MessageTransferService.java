package ru.service.jchat.services.transfer;

import ru.service.jchat.models.entities.MessageEntity;
import ru.service.jchat.models.response.dto.MessageDTO;

public interface MessageTransferService {
    MessageDTO messageToDto(MessageEntity message);
}
