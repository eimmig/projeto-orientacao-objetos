package com.br.Ecoleta.service;

import com.br.Ecoleta.model.Veiculo;
import com.br.Ecoleta.repository.VeiculoRepository;

public class VeiculoService extends GenericService<Veiculo, Long> {

    public VeiculoService(VeiculoRepository repository) {
        super(repository);
    }
}