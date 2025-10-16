package com.challenge.userpostapi.infrastructure.persistence.repository.interfaces;

import com.challenge.userpostapi.domain.enums.RoleEnum;
import com.challenge.userpostapi.infrastructure.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleEntityInterfaceRepository extends JpaRepository<RoleEntity, Long> {
    Boolean existsRoleEntityByName(RoleEnum name);
    Optional<RoleEntity> findByName(RoleEnum name);
}
