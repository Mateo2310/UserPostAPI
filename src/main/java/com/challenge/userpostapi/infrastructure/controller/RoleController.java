package com.challenge.userpostapi.infrastructure.controller;

import com.challenge.userpostapi.application.dto.ResponseGeneric;
import com.challenge.userpostapi.application.dto.RoleRequestDTO;
import com.challenge.userpostapi.application.dto.RoleResponseDTO;
import com.challenge.userpostapi.application.service.interfaces.RoleServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Roles", description = "Endpoints para gestión de roles de usuario")
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleServiceInterface roleService;

    @Operation(summary = "Crea un nuevo rol", description = "Crea un rol en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol creado correctamente",
                    content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody @Valid RoleRequestDTO roleCreateDTO) {
        this.roleService.save(roleCreateDTO);
        return ResponseEntity.ok().body(new ResponseGeneric<>("success", "Role created", null));
    }

    @Operation(summary = "Obtiene todos los roles", description = "Devuelve una lista con todos los roles registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles obtenidos correctamente",
                    content = @Content(schema = @Schema(implementation = ResponseGeneric.class)))
    })
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<RoleResponseDTO> roles = this.roleService.findAll();
        return ResponseEntity.ok().body(new  ResponseGeneric<>("success", "Roles", roles));
    }

    @Operation(summary = "Elimina un rol por ID", description = "Elimina un rol existente en base a su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol eliminado correctamente",
                    content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        this.roleService.deleteById(id);
        return ResponseEntity.ok().body(new ResponseGeneric<>("success", "Role deleted succesfully", null));
    }


    @Operation(summary = "Obtiene un rol por ID", description = "Busca un rol específico en base a su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol encontrado correctamente",
                    content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getRole(@PathVariable Long id) {
        RoleResponseDTO role = this.roleService.findById(id);
        return ResponseEntity.ok().body(new  ResponseGeneric<>("success", "Role", role));
    }


    @Operation(summary = "Obtiene un rol por ID", description = "Busca un rol específico en base a su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol encontrado correctamente",
                    content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody @Valid RoleRequestDTO roleUpdateDTO) {
        RoleResponseDTO role = this.roleService.update(id, roleUpdateDTO);
        return ResponseEntity.ok().body(new  ResponseGeneric<>("success", "Role updated", role));
    }

    @Operation(summary = "Actualiza un rol completamente", description = "Reemplaza los datos de un rol existente por completo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol actualizado correctamente",
                    content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePartialRole(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        RoleResponseDTO role = this.roleService.partialUpdate(id, updates);
        return ResponseEntity.ok().body(new  ResponseGeneric<>("success", "Role updated", role));
    }
}
