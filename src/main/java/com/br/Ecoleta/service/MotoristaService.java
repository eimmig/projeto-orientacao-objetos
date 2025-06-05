package com.br.Ecoleta.service;

import com.br.Ecoleta.model.Motorista;
import com.br.Ecoleta.repository.MotoristaRepository;

public class MotoristaService extends GenericService<Motorista, Long> {

    public MotoristaService(MotoristaRepository repository) {
        super(repository);
    }
}