package com.challenge.userpostapi.infrastructure.persistence.repository.interfaces;

import com.challenge.userpostapi.infrastructure.persistence.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostEntityInterfaceRepository extends JpaRepository<PostEntity, Long> {
    PostEntity findByIdAndAuthorId(Long id, Long authorId);
    List<PostEntity> findAllByAuthorId(Long authorId);
    void deleteByIdAndAuthorId(Long id, Long authorId);
}
