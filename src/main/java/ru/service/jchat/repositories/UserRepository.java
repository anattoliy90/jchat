package ru.service.jchat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.service.jchat.models.entities.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByEmailContaining(String email);

    Optional<UserEntity> findByEmail(String email);

    @Query("select u from UserEntity u " +
            "where (:firstName is null or u.firstName like concat ('%', :firstName, '%'))"
            + " and (:lastName is null or u.lastName like concat('%', :lastName, '%'))")
    List<UserEntity> searchByFirstAndOrLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
