package com.br.ecoleta.controller;

import com.br.ecoleta.model.Rota;
import com.br.ecoleta.service.RotaService;

public class RotaController extends GenericController<Rota, Long> {

    public RotaController(RotaService genericService) {
        super(genericService);
    }
}
