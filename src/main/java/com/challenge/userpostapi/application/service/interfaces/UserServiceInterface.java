package com.challenge.userpostapi.application.service.interfaces;

import com.challenge.userpostapi.application.dto.UserRequestDTO;
import com.challenge.userpostapi.application.dto.UserResponseDTO;
import com.challenge.userpostapi.domain.model.UserModel;

public interface UserServiceInterface extends CrudServiceInterface<UserRequestDTO, UserResponseDTO> {
    String save(UserRequestDTO request);
    UserModel findByUsername(String username);
}
