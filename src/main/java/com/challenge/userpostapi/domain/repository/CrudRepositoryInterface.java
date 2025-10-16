package com.challenge.userpostapi.domain.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepositoryInterface<T> {
    T save(T model);
    Optional<T> findById(Long id);
    List<T> findAll();
    void deleteById(Long id);
}
