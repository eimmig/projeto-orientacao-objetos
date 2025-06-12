package com.br.ecoleta.controller;

import com.br.ecoleta.model.Coleta;
import com.br.ecoleta.model.Rota;
import com.br.ecoleta.service.RotaService;
import java.util.List;

public class RotaController extends GenericController<Rota, Long> {

    private final RotaService rotaService;

    public RotaController(RotaService genericService) {
        super(genericService);
        this.rotaService = genericService;
    }

    /**
     * Obtém as coletas pendentes para uma rota específica.
     *
     * @param rota A rota para a qual se deseja obter as coletas pendentes.
     * @return Uma lista de coletas pendentes associadas à rota.
     */
    public List<Coleta> getColetasPendentes(Rota rota) {
        return rotaService.getColetasPendentes(rota);
    }
}
