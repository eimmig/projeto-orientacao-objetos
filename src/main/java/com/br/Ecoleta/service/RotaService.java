package com.br.Ecoleta.service;

import com.br.Ecoleta.model.Rota;
import com.br.Ecoleta.repository.RotaRepository;

public class RotaService extends GenericService<Rota, Long> {

    public RotaService(RotaRepository repository) {
        super(repository);
    }
}