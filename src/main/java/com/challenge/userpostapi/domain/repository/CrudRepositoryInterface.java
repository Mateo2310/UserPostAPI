package com.challenge.userpostapi.domain.repository;

import java.util.List;

public interface CrudRepositoryInterface<T> {
    T save(T model);
    T findById(Long id);
    List<T> findAll();
    void deleteById(Long id);
}
