package com.br.Ecoleta.repository;

import com.br.Ecoleta.model.Cliente;
import jakarta.persistence.EntityManager;

public class ClienteRepository extends AbstractJpaRepository<Cliente, Long> {

    public ClienteRepository(EntityManager entityManager) {
        super(entityManager);
    }
}