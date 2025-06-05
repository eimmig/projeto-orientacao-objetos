package com.br.Ecoleta.controller;

import com.br.Ecoleta.model.Veiculo;
import com.br.Ecoleta.service.VeiculoService;

public class VeiculoController extends GenericController<Veiculo, Long> {

    public VeiculoController(VeiculoService genericService) {
        super(genericService);
    }
}