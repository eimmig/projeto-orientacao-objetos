package com.br.Ecoleta.repository;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T, ID> {

    T save(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    void deleteById(ID id);

    boolean existsById(ID id);

    T update(T entity);
}