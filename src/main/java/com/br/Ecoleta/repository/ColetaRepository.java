package com.br.Ecoleta.repository;

import com.br.Ecoleta.model.Coleta;
import jakarta.persistence.EntityManager;

public class ColetaRepository extends AbstractJpaRepository<Coleta, Long> {

    public ColetaRepository(EntityManager entityManager) {
        super(entityManager);
    }
}