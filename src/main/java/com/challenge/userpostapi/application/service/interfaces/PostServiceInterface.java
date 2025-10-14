package com.challenge.userpostapi.application.service.interfaces;

import com.challenge.userpostapi.application.dto.PostRequestDTO;
import com.challenge.userpostapi.application.dto.PostResponseDTO;

import java.util.List;

public interface PostServiceInterface {
    void save(PostRequestDTO request, Long authorId);
    void deleteByIdAndAuthorId(Long id, Long authorId);
    PostResponseDTO findByIdAndAuthorId(Long id, Long authorId);
    List<PostResponseDTO> findAll(Long authorId);
}
