package com.br.ecoleta.service;

import com.br.ecoleta.model.Veiculo;
import com.br.ecoleta.repository.VeiculoRepository;

import java.util.List;

public class VeiculoService extends GenericService<Veiculo, Long> {

    public VeiculoService(VeiculoRepository repository) {
        super(repository);
    }

    @Override
    protected String getEntityType() {
        return Veiculo.class.getName();
    }

    /**
     * Busca todos os veículos que não estão vinculados a nenhuma rota.
     */
    public List<Veiculo> getVeiculosSemRotaOuMotorista() {
        VeiculoRepository repo = (VeiculoRepository) this.repository;
        return repo.findVeiculosSemRotaOuMotorista();
    }
}