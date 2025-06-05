package com.br.Ecoleta.repository;

import com.br.Ecoleta.model.PontoDeColeta;
import jakarta.persistence.EntityManager;

public class PontoDeColetaRepository extends AbstractJpaRepository<PontoDeColeta, Long> {

    public PontoDeColetaRepository(EntityManager entityManager) {
        super(entityManager);
    }
}