package ru.service.jchat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.service.jchat.models.entities.ChatEntity;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
    @Query("select chat from ChatEntity chat"
            + " where (:title is null or chat.title like concat ('%', :title, '%'))")
    List<ChatEntity> searchByTitle(@Param("title") String title);
}
