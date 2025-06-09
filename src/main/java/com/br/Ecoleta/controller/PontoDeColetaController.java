package com.br.ecoleta.controller;

import com.br.ecoleta.model.PontoDeColeta;
import com.br.ecoleta.service.PontoDeColetaService;

public class PontoDeColetaController extends GenericController<PontoDeColeta, Long> {

    public PontoDeColetaController(PontoDeColetaService genericService) {
        super(genericService);
    }
}