package com.br.ecoleta.repository;

import com.br.ecoleta.model.Motorista;
import jakarta.persistence.EntityManager;

public class MotoristaRepository extends AbstractJpaRepository<Motorista, Long> {

    public MotoristaRepository(EntityManager entityManager) {
        super(entityManager);
    }
}