package com.br.ecoleta.controller;

import java.util.List;

import com.br.ecoleta.model.Veiculo;
import com.br.ecoleta.service.VeiculoService;

public class VeiculoController extends GenericController<Veiculo, Long> {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService genericService) {
        super(genericService);
        this.veiculoService = genericService;
    }

    /**
     * Busca veículos que não estão vinculados a nenhuma rota/motorista.
     */
    public List<Veiculo> getVeiculosSemRotaOuMotorista() {
        return veiculoService.getVeiculosSemRotaOuMotorista();
    }
}