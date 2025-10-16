package com.challenge.userpostapi.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    @NotNull(message = "The username is required")
    @NotBlank(message = "The username is required")
    @NotEmpty(message = "The username is required")
    @Email(message = "Invalid email")
    private String username;

    @NotNull(message = "The password is required")
    @NotBlank(message = "The password is required")
    @NotEmpty(message = "The password is required")
    private String password;
}

