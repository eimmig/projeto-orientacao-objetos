package com.br.Ecoleta.controller;

import com.br.Ecoleta.model.Cliente;
import com.br.Ecoleta.service.ClienteService;

public class ClienteController extends GenericController<Cliente, Long> {

    public ClienteController(ClienteService genericService) {
        super(genericService);
    }
}