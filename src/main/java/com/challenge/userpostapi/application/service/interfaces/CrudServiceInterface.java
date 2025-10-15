package com.challenge.userpostapi.application.service.interfaces;

import java.util.List;

public interface CrudServiceInterface<T, D> {
    void deleteById(Long id);
    D findById(Long id);
    List<D> findAll();
}
