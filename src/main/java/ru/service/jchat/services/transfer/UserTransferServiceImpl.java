package ru.service.jchat.services.transfer;

import org.springframework.stereotype.Service;
import ru.service.jchat.models.entities.UserEntity;
import ru.service.jchat.models.response.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserTransferServiceImpl implements UserTransferService {
    public UserDTO userToDto(UserEntity user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getAvatar(),
                user.getEmail(),
                user.getConfirmed(),
                user.getCreated(),
                user.getUpdated()
        );
    }

    public List<UserDTO> userListToDto(List<UserEntity> user) {
        List<UserDTO> users = new ArrayList<>();

        for (UserEntity item : user) {
            UserDTO userDto = new UserDTO(
                    item.getId(),
                    item.getFirstName(),
                    item.getLastName(),
                    item.getAvatar(),
                    item.getEmail(),
                    item.getConfirmed(),
                    item.getCreated(),
                    item.getUpdated()
            );

            users.add(userDto);
        }

        return users;
    }
}
