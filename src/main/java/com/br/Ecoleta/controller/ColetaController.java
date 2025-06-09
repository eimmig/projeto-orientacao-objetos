package com.br.ecoleta.controller;

import com.br.ecoleta.model.Coleta;
import com.br.ecoleta.service.ColetaService;

public class ColetaController extends GenericController<Coleta, Long> {

    public ColetaController(ColetaService genericService) {
        super(genericService);
    }
}