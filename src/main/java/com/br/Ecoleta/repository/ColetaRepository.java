package com.br.ecoleta.repository;

import com.br.ecoleta.model.Coleta;
import jakarta.persistence.EntityManager;

public class ColetaRepository extends AbstractJpaRepository<Coleta, Long> {

    public ColetaRepository(EntityManager entityManager) {
        super(entityManager);
    }
}