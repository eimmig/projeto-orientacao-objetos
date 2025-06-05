package com.br.Ecoleta.service;

import com.br.Ecoleta.model.PontoDeColeta;
import com.br.Ecoleta.repository.PontoDeColetaRepository;

public class PontoDeColetaService extends GenericService<PontoDeColeta, Long> {

    public PontoDeColetaService(PontoDeColetaRepository repository) {
        super(repository);
    }
}