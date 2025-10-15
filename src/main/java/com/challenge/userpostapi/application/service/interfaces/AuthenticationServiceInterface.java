package com.challenge.userpostapi.application.service.interfaces;

import com.challenge.userpostapi.application.dto.AuthenticationResponse;
import com.challenge.userpostapi.application.dto.UserLoginDTO;
import com.challenge.userpostapi.application.dto.UserRequestDTO;

public interface AuthenticationServiceInterface {
    AuthenticationResponse register(UserRequestDTO request);
    AuthenticationResponse login(UserLoginDTO request);
}
