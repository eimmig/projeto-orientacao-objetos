package com.br.ecoleta.repository;

import com.br.ecoleta.model.Cliente;
import jakarta.persistence.EntityManager;

public class ClienteRepository extends AbstractJpaRepository<Cliente, Long> {

    public ClienteRepository(EntityManager entityManager) {
        super(entityManager);
    }
}