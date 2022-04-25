package ru.service.jchat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.service.jchat.models.entities.RoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
