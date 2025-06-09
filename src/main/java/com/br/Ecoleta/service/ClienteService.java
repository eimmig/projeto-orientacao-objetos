package com.br.ecoleta.service;

import com.br.ecoleta.model.Cliente;
import com.br.ecoleta.repository.ClienteRepository;

public class ClienteService extends GenericService<Cliente, Long> {

    public ClienteService(ClienteRepository repository) {
        super(repository);
    }
}