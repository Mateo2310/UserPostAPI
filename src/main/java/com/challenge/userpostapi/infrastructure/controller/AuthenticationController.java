package com.challenge.userpostapi.infrastructure.controller;

import com.challenge.userpostapi.application.dto.UserLoginDTO;
import com.challenge.userpostapi.application.dto.UserRequestDTO;
import com.challenge.userpostapi.application.service.interfaces.AuthenticationServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationServiceInterface authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRequestDTO request) {
        return ResponseEntity.ok(this.authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginDTO userLoginDTO){
        return ResponseEntity.ok(this.authenticationService.login(userLoginDTO));
    }
}