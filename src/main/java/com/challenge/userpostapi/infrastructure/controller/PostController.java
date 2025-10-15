package com.challenge.userpostapi.infrastructure.controller;

import com.challenge.userpostapi.application.dto.*;
import com.challenge.userpostapi.application.service.interfaces.PostServiceInterface;
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
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostServiceInterface postService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid PostRequestDTO postRequestDTO, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long authorId = userDetails.getUserModel().getId();
        this.postService.save(postRequestDTO, authorId);
        return ResponseEntity.ok().body(new ResponseGeneric<>("success", "Post created", null));
    }

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
