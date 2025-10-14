package com.challenge.userpostapi.infrastructure.persistence.repository.impl;

import com.challenge.userpostapi.domain.model.PostModel;
import com.challenge.userpostapi.domain.repository.PostRepositoryInterface;
import com.challenge.userpostapi.infrastructure.mapper.PostMapper;
import com.challenge.userpostapi.infrastructure.persistence.entity.PostEntity;
import com.challenge.userpostapi.infrastructure.persistence.repository.interfaces.PostEntityInterfaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostEntityRepositoryImpl implements PostRepositoryInterface {
    private final PostEntityInterfaceRepository postEntityInterfaceRepository;
    private final PostMapper postMapper;

    @Override
    public PostModel findByIdAndAuthorId(Long id, Long userId) {
        return this.postMapper.toPostModel(this.postEntityInterfaceRepository.findByIdAndAuthorId(id, userId));
    }

    @Override
    public List<PostModel> findAllByUserId(Long userId) {
        return this.postEntityInterfaceRepository.findAllByAuthorId(userId).stream()
                .map(this.postMapper::toPostModel)
                .toList();
    }

    @Override
    public void deleteByIdAndAuthorId(Long id, Long userId) {
        this.postEntityInterfaceRepository.deleteByIdAndAuthorId(id, userId);
    }

    @Override
    public void saveAll(List<PostModel> postModels) {
        List<PostEntity> postEntities = postModels.stream()
                .map(this.postMapper::toPostEntity)
                .toList();
        this.postEntityInterfaceRepository.saveAll(postEntities);
    }

    @Override
    public PostModel save(PostModel model) {
        return this.postMapper.toPostModel(this.postEntityInterfaceRepository.save(this.postMapper.toPostEntity(model)));
    }

    @Override
    public PostModel findById(Long id) {
        return this.postMapper.toPostModel(this.postEntityInterfaceRepository.findById(id).orElse(null));
    }

    @Override
    public List<PostModel> findAll() {
        return this.postEntityInterfaceRepository.findAll().stream()
                .map(this.postMapper::toPostModel)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        this.postEntityInterfaceRepository.deleteById(id);
    }
}
