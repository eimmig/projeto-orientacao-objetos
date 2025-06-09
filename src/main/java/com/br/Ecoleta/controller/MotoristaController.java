package com.br.ecoleta.controller;

import com.br.ecoleta.model.Motorista;
import com.br.ecoleta.service.MotoristaService;

public class MotoristaController extends GenericController<Motorista, Long> {

    public MotoristaController(MotoristaService genericService) {
        super(genericService);
    }
}