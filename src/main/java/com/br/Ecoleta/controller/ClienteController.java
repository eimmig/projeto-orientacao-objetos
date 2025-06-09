package com.br.ecoleta.controller;

import com.br.ecoleta.model.Cliente;
import com.br.ecoleta.service.ClienteService;

public class ClienteController extends GenericController<Cliente, Long> {

    public ClienteController(ClienteService genericService) {
        super(genericService);
    }
}