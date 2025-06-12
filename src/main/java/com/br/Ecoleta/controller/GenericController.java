package com.br.ecoleta.controller;

import com.br.ecoleta.service.GenericService;
import com.br.ecoleta.util.ConsoleUtils;
import com.br.ecoleta.exception.EntityNotFoundException;
import com.br.ecoleta.exception.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public abstract class GenericController<T, D> {

    protected final GenericService<T, D> genericService;
    protected final Scanner scanner;

    protected GenericController(GenericService<T, D> genericService) {
        this.genericService = genericService;
        this.scanner = new Scanner(System.in);
    }

    public T save(T item) throws ValidationException {
        ConsoleUtils.println("Tentando salvar item...");
        T createdItem = genericService.save(item);
        ConsoleUtils.println("Item criado com sucesso!");
        return createdItem;
    }

    public Optional<T> getById(D id) {
        ConsoleUtils.println("Buscando item com ID: " + id);
        Optional<T> item = genericService.getById(id);
        if (item.isPresent()) {
            ConsoleUtils.println("Item encontrado: " + item.get());
        } else {
            ConsoleUtils.println("Item com ID " + id + " não encontrado.");
        }
        return item;
    }

    public List<T> getAll() {
        ConsoleUtils.println("Buscando todos os itens...");
        List<T> items = genericService.getAll();
        if (items.isEmpty()) {
            ConsoleUtils.println("Nenhum item encontrado.");
        } else {
            ConsoleUtils.println("Itens encontrados:");
            items.forEach(item -> ConsoleUtils.println(item.toString()));
        }
        return items;
    }

    public T update(D id, T updatedItem) throws EntityNotFoundException, ValidationException {
        ConsoleUtils.println("Tentando atualizar item com ID: " + id);
        T item = genericService.update(id, updatedItem);
        ConsoleUtils.println("Item atualizado com sucesso!");
        return item;
    }

    public boolean delete(D id) throws EntityNotFoundException, ValidationException {
        ConsoleUtils.println("Tentando remover item com ID: " + id);
        boolean deleted = genericService.delete(id);
        if (deleted) {
            ConsoleUtils.println("Item removido com sucesso!");
        } else {
            throw new EntityNotFoundException("Item", (Long) id);
        }
        return deleted;
    }
}