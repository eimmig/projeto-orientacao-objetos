package com.br.Ecoleta.service;

import com.br.Ecoleta.model.Cliente;
import com.br.Ecoleta.repository.ClienteRepository;

public class ClienteService extends GenericService<Cliente, Long> {

    public ClienteService(ClienteRepository repository) {
        super(repository);
    }
}