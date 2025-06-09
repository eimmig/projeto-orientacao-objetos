package com.br.ecoleta.repository;

import com.br.ecoleta.model.Rota;
import jakarta.persistence.EntityManager;

public class RotaRepository extends AbstractJpaRepository<Rota, Long> {

    public RotaRepository(EntityManager entityManager) {
        super(entityManager);
    }
}