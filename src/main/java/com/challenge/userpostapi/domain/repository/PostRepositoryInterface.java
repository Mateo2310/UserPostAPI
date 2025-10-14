package com.challenge.userpostapi.domain.repository;

import com.challenge.userpostapi.domain.model.PostModel;

import java.util.List;

public interface PostRepositoryInterface extends CrudRepositoryInterface<PostModel> {
    PostModel findByIdAndAuthorId(Long id, Long userId);
    List<PostModel> findAllByUserId(Long userId);
    void deleteByIdAndAuthorId(Long id, Long userId);
    void saveAll(List<PostModel> postModels);
}
