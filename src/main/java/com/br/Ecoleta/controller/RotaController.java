package com.br.Ecoleta.controller;

import com.br.Ecoleta.model.Rota;
import com.br.Ecoleta.service.RotaService;

public class RotaController extends GenericController<Rota, Long> {

    public RotaController(RotaService genericService) {
        super(genericService);
    }
}
