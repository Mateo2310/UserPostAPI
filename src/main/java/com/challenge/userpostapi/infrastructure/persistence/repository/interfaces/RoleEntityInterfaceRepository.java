package com.challenge.userpostapi.infrastructure.persistence.repository.interfaces;

import com.challenge.userpostapi.infrastructure.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleEntityInterfaceRepository extends JpaRepository<RoleEntity, Long> {
}
