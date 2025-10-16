package com.challenge.userpostapi.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserLoginDTO {
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @NotNull(message = "El nombre de usuario es obligatorio")
    private String username;

    @NotBlank(message = "La contraseña es obligatorio")
    @NotNull(message = "La contraseña es obligatorio")
    private String password;
}

