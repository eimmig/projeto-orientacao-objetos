package com.br.ecoleta.service;

import com.br.ecoleta.repository.GenericRepository;
import com.br.ecoleta.exception.EntityNotFoundException;
import com.br.ecoleta.exception.ValidationException;
import java.util.List;
import java.util.Optional;

public abstract class GenericService<T, ID> {

    protected final GenericRepository<T, ID> repository;

    public GenericService(GenericRepository<T, ID> repository) {
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

    public Optional<T> getById(ID id) {
        return repository.findById(id);
    }

    public boolean delete(ID id) throws EntityNotFoundException {
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

    public T update(ID id, T updatedItem) throws EntityNotFoundException, ValidationException {
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