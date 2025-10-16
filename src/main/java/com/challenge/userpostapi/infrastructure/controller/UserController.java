package com.challenge.userpostapi.infrastructure.controller;

import com.challenge.userpostapi.application.dto.ResponseGeneric;
import com.challenge.userpostapi.application.dto.UserRequestDTO;
import com.challenge.userpostapi.application.dto.UserResponseDTO;
import com.challenge.userpostapi.application.service.interfaces.UserServiceInterface;
import com.challenge.userpostapi.domain.model.UserModel;
import com.challenge.userpostapi.infrastructure.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User", description = "Operaciones sobre usuarios")
public class UserController {
    private final UserServiceInterface userService;

    @Operation(summary = "Obtiene la lista de todos los usuarios", description = "Solo accesible por administradores.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios obtenidos correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "500", description = "Error interno inesperado", content = @Content(schema = @Schema(implementation = ResponseGeneric.class)))
    })
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<UserResponseDTO> users = this.userService.findAll();
        return ResponseEntity.ok(new ResponseGeneric<List<UserResponseDTO>>("success", "Users", users){});
    }

    @Operation(summary = "Obtiene la información de un usuario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario obtenido correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "500", description = "Error interno inesperado", content = @Content(schema = @Schema(implementation = ResponseGeneric.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        UserResponseDTO userResponseDTO = this.userService.findById(id);
        return ResponseEntity.ok(new ResponseGeneric<UserResponseDTO>("success", "User", userResponseDTO){});
    }

    @Operation(summary = "Elimina un usuario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "500", description = "Error interno inesperado", content = @Content(schema = @Schema(implementation = ResponseGeneric.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        this.userService.deleteById(id);
        return ResponseEntity.ok().body(new ResponseGeneric<>("success", "User deleted successfully", null));
    }

    @Operation(summary = "Actualiza completamente un usuario", description = "Si no sos admin, solo podés actualizar tu propio usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "400", description = "Errores de validación", content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "500", description = "Error interno inesperado", content = @Content(schema = @Schema(implementation = ResponseGeneric.class)))
    })
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

    @Operation(summary = "Actualiza parcialmente un usuario", description = "Permite modificar campos individuales. Si no sos admin, solo podés actualizar tu propio usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado parcialmente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "400", description = "Errores de validación o campos inválidos", content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "500", description = "Error interno inesperado", content = @Content(schema = @Schema(implementation = ResponseGeneric.class)))
    })
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
