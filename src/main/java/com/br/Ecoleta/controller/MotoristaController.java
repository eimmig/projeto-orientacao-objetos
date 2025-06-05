package com.br.Ecoleta.controller;

import com.br.Ecoleta.model.Motorista;
import com.br.Ecoleta.service.MotoristaService;

public class MotoristaController extends GenericController<Motorista, Long> {

    public MotoristaController(MotoristaService genericService) {
        super(genericService);
    }
}