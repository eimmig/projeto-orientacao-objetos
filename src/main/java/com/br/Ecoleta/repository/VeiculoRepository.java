package com.br.ecoleta.repository;

import com.br.ecoleta.model.Veiculo;
import jakarta.persistence.EntityManager;
import java.util.List;

public class VeiculoRepository extends AbstractJpaRepository<Veiculo, Long> {

    public VeiculoRepository(EntityManager entityManager) {
        super(entityManager);
    }

    /**
     * Busca todos os veículos que não estão vinculados a nenhuma rota.
     * Um veículo está desvinculado se não possui nenhuma rota associada.
     */
    public List<Veiculo> findVeiculosSemRotaOuMotorista() {
        return getEntityManager()
            .createQuery("SELECT v FROM Veiculo v WHERE v.rotas IS EMPTY", Veiculo.class)
            .getResultList();
    }
}