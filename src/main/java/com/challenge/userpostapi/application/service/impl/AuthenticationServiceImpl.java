package com.challenge.userpostapi.application.service.impl;

import com.challenge.userpostapi.application.dto.AuthenticationResponse;
import com.challenge.userpostapi.application.dto.UserLoginDTO;
import com.challenge.userpostapi.application.dto.UserRequestDTO;
import com.challenge.userpostapi.application.service.interfaces.AuthenticationServiceInterface;
import com.challenge.userpostapi.application.service.interfaces.UserServiceInterface;
import com.challenge.userpostapi.domain.exception.BusinessException;
import com.challenge.userpostapi.domain.model.UserModel;
import com.challenge.userpostapi.infrastructure.security.JwtService;
import com.challenge.userpostapi.infrastructure.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationServiceInterface {
    private final UserServiceInterface userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponse register(UserRequestDTO request) {
        String token = this.userService.save(request);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthenticationResponse login(UserLoginDTO request) {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        UserModel userModel = this.userService.findByUsername(request.getUsername());
        if (userModel == null){
            throw new BusinessException(request.getUsername());
        }
        String token = this.jwtService.generateToken(new UserDetailsImpl(userModel));
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
