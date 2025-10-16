package com.challenge.userpostapi.infrastructure.controller;

import com.challenge.userpostapi.application.dto.*;
import com.challenge.userpostapi.application.service.interfaces.PostServiceInterface;
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
@RequestMapping("/post")
@RequiredArgsConstructor
@Tag(name = "Posts", description = "Endpoints para gestión de posts de usuarios")
public class PostController {
    private final PostServiceInterface postService;

    @Operation(summary = "Crea un nuevo post", description = "Crea un post asociado al usuario autenticado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post creado correctamente",
                    content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid PostRequestDTO postRequestDTO, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long authorId = userDetails.getUserModel().getId();
        this.postService.save(postRequestDTO, authorId);
        return ResponseEntity.ok().body(new ResponseGeneric<>("success", "Post created", null));
    }

    @Operation(summary = "Obtiene todos los posts del usuario autenticado", description = "Si es ADMIN devuelve todos los posts.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posts obtenidos correctamente",
                    content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> findAll(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserModel userAuth = userDetails.getUserModel();
        List<PostResponseDTO> posts;
        if (userAuth.getRoleModel().getName().equals("ADMIN")) {
            posts = this.postService.findAll();
        } else {
            Long authorId = userAuth.getId();
            posts = this.postService.findAllByAuthorId(authorId);
        }
        return ResponseEntity.ok().body(new  ResponseGeneric<>("success", "Posts", posts));
    }

    @Operation(summary = "Elimina un post por ID", description = "Si es ADMIN puede eliminar cualquiera, sino solo los propios.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post eliminado correctamente",
                    content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Post no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserModel userAuth = userDetails.getUserModel();
        if (userAuth.getRoleModel().getName().equals("ADMIN")) {
            this.postService.deleteById(id);
        } else {
            Long authorId = userAuth.getId();
            this.postService.deleteByIdAndAuthorId(id, authorId);
        }
        return ResponseEntity.ok().body(new ResponseGeneric<>("success", "Post deleted succesfully", null));
    }

    @Operation(summary = "Obtiene un post por ID", description = "Si es ADMIN puede obtener cualquiera, sino solo los propios.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post encontrado correctamente",
                    content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Post no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserModel userAuth = userDetails.getUserModel();
        PostResponseDTO post;
        if (userAuth.getRoleModel().getName().equals("ADMIN")) {
            post = this.postService.findById(id);
        } else {
            Long authorId = userAuth.getId();
            post = this.postService.findByIdAndAuthorId(id, authorId);
        }
        return ResponseEntity.ok().body(new  ResponseGeneric<>("success", "Post", post));
    }

    @Operation(summary = "Actualiza un post completamente", description = "Si es ADMIN puede actualizar cualquiera, sino solo los propios.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post actualizado correctamente",
                    content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Post no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(
            @PathVariable Long id,
            @RequestBody @Valid PostRequestDTO postDTO,
            Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserModel userAuth = userDetails.getUserModel();
        PostResponseDTO post;
        if (userAuth.getRoleModel().getName().equals("ADMIN")) {
            post = this.postService.update(id, postDTO);
        } else {
            Long authorId = userAuth.getId();
            post = this.postService.updateByAuthorId(id, authorId, postDTO);
        }
        return ResponseEntity.ok().body(new  ResponseGeneric<>("success", "Post updated", post));
    }

    @Operation(summary = "Actualiza parcialmente un post", description = "Si es ADMIN puede actualizar cualquiera, sino solo los propios.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post actualizado parcialmente",
                    content = @Content(schema = @Schema(implementation = ResponseGeneric.class))),
            @ApiResponse(responseCode = "400", description = "Error en el body", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Post no encontrado", content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdatePost(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates,
            Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserModel userAuth = userDetails.getUserModel();
        PostResponseDTO post;
        if (userAuth.getRoleModel().getName().equals("ADMIN")) {
            post = this.postService.partialUpdate(id, updates);
        } else {
            Long authorId = userAuth.getId();
            post = this.postService.partialUpdateByAuthorId(id, authorId, updates);
        }
        return ResponseEntity.ok().body(new  ResponseGeneric<>("success", "Post updated", post));
    }
}
