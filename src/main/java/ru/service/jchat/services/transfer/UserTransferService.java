package ru.service.jchat.services.transfer;

import ru.service.jchat.models.entities.UserEntity;
import ru.service.jchat.models.response.dto.UserDTO;

import java.util.List;

public interface UserTransferService {
    UserDTO userToDto(UserEntity user);

    List<UserDTO> userListToDto(List<UserEntity> user);
}
