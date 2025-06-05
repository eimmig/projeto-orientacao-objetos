package com.br.Ecoleta.repository;

import com.br.Ecoleta.model.Motorista;
import jakarta.persistence.EntityManager;

public class MotoristaRepository extends AbstractJpaRepository<Motorista, Long> {

    public MotoristaRepository(EntityManager entityManager) {
        super(entityManager);
    }
}