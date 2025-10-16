package com.challenge.userpostapi.infrastructure.controller;

import com.challenge.userpostapi.application.dto.ResponseGeneric;
import com.challenge.userpostapi.application.dto.UserRequestDTO;
import com.challenge.userpostapi.application.dto.UserResponseDTO;
import com.challenge.userpostapi.application.service.interfaces.UserServiceInterface;
import com.challenge.userpostapi.domain.model.UserModel;
import com.challenge.userpostapi.infrastructure.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceInterface userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<UserResponseDTO> users = this.userService.findAll();
        return ResponseEntity.ok(new ResponseGeneric<List<UserResponseDTO>>("success", "Users", users){});
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        UserResponseDTO userResponseDTO = this.userService.findById(id);
        return ResponseEntity.ok(new ResponseGeneric<UserResponseDTO>("success", "User", userResponseDTO){});
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        this.userService.deleteById(id);
        return ResponseEntity.ok().body(new ResponseGeneric<>("success", "User deleted successfully", null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody @Valid UserRequestDTO userRequestDTO,
            Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserModel userAuth = userDetails.getUserModel();
        UserResponseDTO userResponseDTO;
        if (userAuth.getRoleModel().getName().equals("ADMIN")) {
            userResponseDTO = this.userService.update(id, userRequestDTO);
        } else {
            Long authorId = userAuth.getId();
            userResponseDTO = this.userService.update(authorId, userRequestDTO);
        }
        return ResponseEntity.ok().body(new  ResponseGeneric<>("success", "User updated", userResponseDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePartialUser(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates,
            Authentication authentication
    ) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserModel userAuth = userDetails.getUserModel();
        UserResponseDTO userResponseDTO;
        if (userAuth.getRoleModel().getName().equals("ADMIN")) {
            userResponseDTO = this.userService.partialUpdate(id, updates);
        } else {
            Long authorId = userAuth.getId();
            userResponseDTO = this.userService.partialUpdate(authorId, updates);
        }
        return ResponseEntity.ok().body(new  ResponseGeneric<>("success", "User updated", userResponseDTO));
    }
}
