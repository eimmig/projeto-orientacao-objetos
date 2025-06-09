package com.br.ecoleta.repository;

import com.br.ecoleta.model.PontoDeColeta;
import jakarta.persistence.EntityManager;

public class PontoDeColetaRepository extends AbstractJpaRepository<PontoDeColeta, Long> {

    public PontoDeColetaRepository(EntityManager entityManager) {
        super(entityManager);
    }
}