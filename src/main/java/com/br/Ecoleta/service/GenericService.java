package com.br.Ecoleta.service;

import com.br.Ecoleta.repository.GenericRepository;
import java.util.List;
import java.util.Optional;

public abstract class GenericService<T, ID> {

    protected final GenericRepository<T, ID> repository;

    public GenericService(GenericRepository<T, ID> repository) {
        this.repository = repository;
    }

    public T save(T item) throws Exception {
        System.out.println("Serviço: Tentando salvar item...");
        return repository.save(item);
    }

    public List<T> getAll() {
        System.out.println("Serviço: Buscando todos os itens...");
        return repository.findAll();
    }

    public Optional<T> getById(ID id) {
        System.out.println("Serviço: Buscando item por ID...");
        return repository.findById(id);
    }

    public boolean delete(ID id) throws RuntimeException {
        System.out.println("Serviço: Tentando deletar item...");
        if (repository.existsById(id)) {
            repository.deleteById(id);
            System.out.println("Serviço: Item deletado com sucesso.");
            return true;
        }
        throw new RuntimeException("Item não encontrado para exclusão.");
    }

    public T update(ID id, T updatedItem) throws RuntimeException {
        System.out.println("Serviço: Tentando atualizar item...");
        if (repository.existsById(id)) {
            return repository.update(updatedItem);
        }
        throw new RuntimeException("Item não encontrado para atualização.");
    }
}