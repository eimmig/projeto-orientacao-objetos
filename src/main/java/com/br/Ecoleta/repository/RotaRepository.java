package com.br.Ecoleta.repository;

import com.br.Ecoleta.model.Rota;
import jakarta.persistence.EntityManager;

public class RotaRepository extends AbstractJpaRepository<Rota, Long> {

    public RotaRepository(EntityManager entityManager) {
        super(entityManager);
    }
}