package com.challenge.userpostapi.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDTO {
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Email(message = "El email debe ser válido")
    private String username;

    @NotBlank(message = "La contraseña es obligatorio")
    private String password;
}

