package com.challenge.userpostapi.infrastructure.controller;

import com.challenge.userpostapi.application.dto.ResponseGeneric;
import com.challenge.userpostapi.application.dto.UserLoginDTO;
import com.challenge.userpostapi.application.dto.UserRequestDTO;
import com.challenge.userpostapi.application.service.interfaces.AuthenticationServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Autenticación", description = "Endpoints para registro e inicio de sesión de usuarios")
public class AuthenticationController {
    private final AuthenticationServiceInterface authenticationService;

    @Operation(
            summary = "Registrar nuevo usuario",
            description = "Registra un nuevo usuario en el sistema con los datos proporcionados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro exitoso",
                    content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación o negocio",
                    content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado",
                    content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "500", description = "Error inesperado",
                    content = @Content(schema = @Schema(implementation = ResponseGeneric.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRequestDTO request) {
        return ResponseEntity.ok(this.authenticationService.register(request));
    }

    @Operation(
            summary = "Iniciar sesión",
            description = "Realiza el login del usuario y devuelve el token JWT u otros datos de autenticación."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso",
                    content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "400", description = "Credenciales inválidas o error de validación",
                    content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "500", description = "Error inesperado",
                    content = @Content(schema = @Schema(implementation = ResponseGeneric.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginDTO userLoginDTO){
        return ResponseEntity.ok(this.authenticationService.login(userLoginDTO));
    }
}