package com.br.ecoleta.service;

import com.br.ecoleta.model.Coleta;
import com.br.ecoleta.repository.ColetaRepository;

public class ColetaService extends GenericService<Coleta, Long> {

    public ColetaService(ColetaRepository repository) {
        super(repository);
    }
}