package ru.service.jchat.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.service.jchat.models.entities.MessageEntity;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    Page<MessageEntity> findByChatId(Long id, Pageable pageable);
}
