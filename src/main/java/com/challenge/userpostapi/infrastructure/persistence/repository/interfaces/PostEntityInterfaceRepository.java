package com.challenge.userpostapi.infrastructure.persistence.repository.interfaces;

import com.challenge.userpostapi.infrastructure.persistence.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostEntityInterfaceRepository extends JpaRepository<PostEntity, Long> {
    Optional<PostEntity> findByIdAndAuthor_Id(Long id, Long authorId);
    List<PostEntity> findAllByAuthor_Id(Long authorId);
    void deleteByIdAndAuthor_Id(Long id, Long authorId);
}
