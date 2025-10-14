package com.challenge.userpostapi.application.service.interfaces;

import com.challenge.userpostapi.application.dto.UserRequestDTO;
import com.challenge.userpostapi.application.dto.UserResponseDTO;

public interface UserServiceInterface extends CrudServiceInterface<UserRequestDTO, UserResponseDTO> {
}
