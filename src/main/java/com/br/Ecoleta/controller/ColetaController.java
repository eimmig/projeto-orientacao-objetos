package com.br.Ecoleta.controller;

import com.br.Ecoleta.model.Coleta;
import com.br.Ecoleta.service.ColetaService;

public class ColetaController extends GenericController<Coleta, Long> {

    public ColetaController(ColetaService genericService) {
        super(genericService);
    }
}