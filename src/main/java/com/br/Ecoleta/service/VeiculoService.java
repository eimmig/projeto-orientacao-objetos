package com.br.ecoleta.service;

import com.br.ecoleta.model.Veiculo;
import com.br.ecoleta.repository.VeiculoRepository;

public class VeiculoService extends GenericService<Veiculo, Long> {

    public VeiculoService(VeiculoRepository repository) {
        super(repository);
    }
}