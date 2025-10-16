package com.challenge.userpostapi.domain.repository;

import com.challenge.userpostapi.domain.enums.RoleEnum;
import com.challenge.userpostapi.domain.model.RoleModel;

import java.util.Optional;

public interface RoleRepositoryInterface extends CrudRepositoryInterface<RoleModel> {
    Boolean existsByName(RoleEnum name);
    Optional<RoleModel> findByName(RoleEnum name);
}
