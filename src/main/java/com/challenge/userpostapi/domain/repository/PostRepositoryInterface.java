package com.challenge.userpostapi.domain.repository;

import com.challenge.userpostapi.domain.model.PostModel;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryInterface extends CrudRepositoryInterface<PostModel> {
    Optional<PostModel> findByIdAndAuthorId(Long id, Long userId);
    List<PostModel> findAllByUserId(Long userId);
    void deleteByIdAndAuthorId(Long id, Long userId);
    void saveAll(List<PostModel> postModels);
}
