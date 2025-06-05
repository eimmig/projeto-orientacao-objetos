package com.br.Ecoleta.service;

import com.br.Ecoleta.model.Coleta;
import com.br.Ecoleta.repository.ColetaRepository;

public class ColetaService extends GenericService<Coleta, Long> {

    public ColetaService(ColetaRepository repository) {
        super(repository);
    }
}