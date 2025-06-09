package com.br.ecoleta.repository;

import com.br.ecoleta.model.Veiculo;
import jakarta.persistence.EntityManager;

public class VeiculoRepository extends AbstractJpaRepository<Veiculo, Long> {

    public VeiculoRepository(EntityManager entityManager) {
        super(entityManager);
    }
}