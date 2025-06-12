package com.br.ecoleta.repository;

import com.br.ecoleta.model.Rota;
import com.br.ecoleta.util.ColetaStatus;
import com.br.ecoleta.model.Coleta;
import jakarta.persistence.EntityManager;

import java.util.List;

public class RotaRepository extends AbstractJpaRepository<Rota, Long> {

    public RotaRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public List<Coleta> findColetasPendentesByRota(Rota rota) {
        return getEntityManager().createQuery(
                "SELECT c FROM Coleta c WHERE c.rota = :rota AND c.status = :status", Coleta.class)
                .setParameter("rota", rota)
                .setParameter("status", ColetaStatus.PENDENTE)
                .getResultList();
    }
}