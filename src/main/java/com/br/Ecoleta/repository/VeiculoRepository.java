package com.br.Ecoleta.repository;

import com.br.Ecoleta.model.Veiculo;
import jakarta.persistence.EntityManager;

public class VeiculoRepository extends AbstractJpaRepository<Veiculo, Long> {

    public VeiculoRepository(EntityManager entityManager) {
        super(entityManager);
    }
}