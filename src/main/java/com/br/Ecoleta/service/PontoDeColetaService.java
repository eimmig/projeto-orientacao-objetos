package com.br.ecoleta.service;

import com.br.ecoleta.model.PontoDeColeta;
import com.br.ecoleta.repository.PontoDeColetaRepository;

public class PontoDeColetaService extends GenericService<PontoDeColeta, Long> {

    public PontoDeColetaService(PontoDeColetaRepository repository) {
        super(repository);
    }
}