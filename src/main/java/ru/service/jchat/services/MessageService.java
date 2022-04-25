package ru.service.jchat.services;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import ru.service.jchat.jwt.JwtAuthentication;
import ru.service.jchat.models.entities.MessageEntity;
import ru.service.jchat.models.entities.UserEntity;
import ru.service.jchat.models.request.MessageCreateRequest;
import ru.service.jchat.models.request.MessagePinRequest;
import ru.service.jchat.models.request.MessageUpdateRequest;
import ru.service.jchat.models.response.dto.MessageDTO;
import ru.service.jchat.repositories.MessageRepository;
import ru.service.jchat.repositories.UserRepository;
import ru.service.jchat.services.transfer.MessageTransferService;

import javax.persistence.EntityNotFoundException;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final MessageTransferService messageTransferService;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, MessageTransferService messageTransferService, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.messageTransferService = messageTransferService;
        this.userRepository = userRepository;
    }

    public MessageDTO getById(Long id) {
        MessageEntity message = messageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Message with id " + id + " not found!"));

        return messageTransferService.messageToDto(message);
    }

    public MessageDTO add(MessageCreateRequest request, JwtAuthentication authInfo) {
        UserEntity user = userRepository.findByEmail(authInfo.getEmail()).orElseThrow(() -> new EntityNotFoundException("User with email " + authInfo.getEmail() + " not found!"));

        MessageEntity message = new MessageEntity();
        message.setUser(user);
        message.setChat(request.getChat());
        message.setText(request.getText());

        return messageTransferService.messageToDto(messageRepository.save(message));
    }

    public MessageDTO update(Long id, MessageUpdateRequest request) {
        MessageEntity message = messageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Message with id " + id + " not found"));
        message.setText(request.getText());
        message.setPinned(request.getPinned());
        message.setUpdated(ZonedDateTime.now());

        return messageTransferService.messageToDto(messageRepository.save(message));
    }

    public void delete(Long id) {
        messageRepository.deleteById(id);
    }

    public Page<MessageDTO> getByChat(Long id, Map<String, String[]> params) {
        int page = params.containsKey("page") ? Integer.parseInt(params.get("page")[0]) : 0;
        int size = params.containsKey("size") ? Integer.parseInt(params.get("size")[0]) : 10;
        Sort defaultSort = Sort.by("id");
        Pageable pageable = PageRequest.of(page, size, defaultSort);
        Page<MessageEntity> messages = messageRepository.findByChatId(id, pageable);

        return new PageImpl<>(
                messages.stream().map(messageTransferService::messageToDto).collect(Collectors.toList()),
                messages.getPageable(),
                messages.getTotalElements()
        );
    }

    public MessageDTO pin(Long id, MessagePinRequest request) {
        MessageEntity message = messageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Message with id " + id + " not found!"));

        message.setPinned(request.getPin());
        messageRepository.save(message);

        return messageTransferService.messageToDto(message);
    }
}
