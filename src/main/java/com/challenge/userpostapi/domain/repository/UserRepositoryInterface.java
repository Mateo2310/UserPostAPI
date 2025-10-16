package com.challenge.userpostapi.domain.repository;

import com.challenge.userpostapi.domain.model.UserModel;

public interface UserRepositoryInterface extends CrudRepositoryInterface<UserModel> {
    UserModel findByUsername(String username);
    Boolean existsByUsername(String username);
}
