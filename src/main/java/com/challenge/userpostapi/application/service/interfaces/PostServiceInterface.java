package com.challenge.userpostapi.application.service.interfaces;

import com.challenge.userpostapi.application.dto.PostRequestDTO;
import com.challenge.userpostapi.application.dto.PostResponseDTO;

import java.util.List;
import java.util.Map;

public interface PostServiceInterface extends CrudServiceInterface<PostRequestDTO, PostResponseDTO> {
    void save(PostRequestDTO request, Long authorId);
    void deleteByIdAndAuthorId(Long id, Long authorId);
    PostResponseDTO findByIdAndAuthorId(Long id, Long authorId);
    List<PostResponseDTO> findAllByAuthorId(Long authorId);
    PostResponseDTO updateByAuthorId(Long id, Long authorId, PostRequestDTO requestDTO);
    PostResponseDTO partialUpdateByAuthorId(Long id, Long authorId, Map<String, Object> partialUpdateDTO);
}
