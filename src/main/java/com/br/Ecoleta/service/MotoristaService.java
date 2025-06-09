package com.br.ecoleta.service;

import com.br.ecoleta.model.Motorista;
import com.br.ecoleta.repository.MotoristaRepository;

public class MotoristaService extends GenericService<Motorista, Long> {

    public MotoristaService(MotoristaRepository repository) {
        super(repository);
    }
}