package com.br.ecoleta.service;

import com.br.ecoleta.repository.GenericRepository;
import com.br.ecoleta.exception.EntityNotFoundException;
import com.br.ecoleta.exception.ValidationException;
import java.util.List;
import java.util.Optional;

public abstract class GenericService<T, D> {

    protected final GenericRepository<T, D> repository;

    protected GenericService(GenericRepository<T, D> repository) {
        this.repository = repository;
    }

    public T save(T item) throws ValidationException {
        try {
            return repository.save(item);
        } catch (Exception e) {
            throw new ValidationException("Erro ao salvar: " + e.getMessage());
        }
    }

    public List<T> getAll() {
        return repository.findAll();
    }

    public Optional<T> getById(D id) {
        return repository.findById(id);
    }

    public boolean delete(D id) throws EntityNotFoundException {
        if (repository.existsById(id)) {
            try {
                repository.deleteById(id);
                return true;
            } catch (Exception e) {
                throw new ValidationException("Erro ao excluir: " + e.getMessage());
            }
        }
        throw new EntityNotFoundException(getEntityType(), (Long) id);
    }

    public T update(D id, T updatedItem) throws EntityNotFoundException, ValidationException {
        if (repository.existsById(id)) {
            try {
                return repository.update(updatedItem);
            } catch (Exception e) {
                throw new ValidationException("Erro ao atualizar: " + e.getMessage());
            }
        }
        throw new EntityNotFoundException(getEntityType(), (Long) id);
    }

    protected abstract String getEntityType();
}