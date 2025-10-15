package com.challenge.userpostapi.infrastructure.controller;

import com.challenge.userpostapi.application.dto.*;
import com.challenge.userpostapi.application.service.interfaces.PostServiceInterface;
import com.challenge.userpostapi.infrastructure.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostServiceInterface postService;

    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody @Valid PostRequestDTO postRequestDTO, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long authorId = userDetails.getUserModel().getId();
        this.postService.save(postRequestDTO, authorId);
        return ResponseEntity.ok().body(new ResponseGeneric<>("success", "Post created", null));
    }

    @GetMapping
    public ResponseEntity<?> findAll(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long authorId = userDetails.getUserModel().getId();
        List<PostResponseDTO> posts = this.postService.findAll(authorId);
        return ResponseEntity.ok().body(new  ResponseGeneric<>("success", "Posts", posts));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long authorId = userDetails.getUserModel().getId();
        this.postService.deleteByIdAndAuthorId(id, authorId);
        return ResponseEntity.ok().body(new ResponseGeneric<>("success", "Post deleted succesfully", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRole(@PathVariable Long id, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long authorId = userDetails.getUserModel().getId();
        PostResponseDTO post = this.postService.findByIdAndAuthorId(id, authorId);
        return ResponseEntity.ok().body(new  ResponseGeneric<>("success", "Post", post));
    }
}
