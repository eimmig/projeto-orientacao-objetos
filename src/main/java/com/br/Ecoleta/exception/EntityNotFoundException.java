package com.br.ecoleta.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityType, Long id) {
        super(entityType + " com ID " + id + " não encontrado.");
    }
}
