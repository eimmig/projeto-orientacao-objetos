package com.br.ecoleta.controller;

import com.br.ecoleta.model.Veiculo;
import com.br.ecoleta.service.VeiculoService;

public class VeiculoController extends GenericController<Veiculo, Long> {

    public VeiculoController(VeiculoService genericService) {
        super(genericService);
    }
}