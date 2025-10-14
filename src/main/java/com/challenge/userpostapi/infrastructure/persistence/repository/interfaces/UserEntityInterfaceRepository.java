package com.challenge.userpostapi.infrastructure.persistence.repository.interfaces;

import com.challenge.userpostapi.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityInterfaceRepository extends JpaRepository<UserEntity, Long> {
}
