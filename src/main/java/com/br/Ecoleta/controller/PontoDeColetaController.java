package com.br.Ecoleta.controller;

import com.br.Ecoleta.model.PontoDeColeta;
import com.br.Ecoleta.service.PontoDeColetaService;

public class PontoDeColetaController extends GenericController<PontoDeColeta, Long> {

    public PontoDeColetaController(PontoDeColetaService genericService) {
        super(genericService);
    }
}