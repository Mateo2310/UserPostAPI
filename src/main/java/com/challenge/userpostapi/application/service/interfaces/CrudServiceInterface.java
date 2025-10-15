package com.challenge.userpostapi.application.service.interfaces;

import java.util.List;
import java.util.Map;

public interface CrudServiceInterface<T, D> {
    void deleteById(Long id);
    D findById(Long id);
    List<D> findAll();
    D update(Long id, T requestDTO);
    D partialUpdate(Long id, Map<String, Object> partialUpdateDTO);
}
