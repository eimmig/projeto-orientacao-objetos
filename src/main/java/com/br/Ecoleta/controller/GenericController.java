package com.br.ecoleta.controller;

import com.br.ecoleta.service.GenericService;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public abstract class GenericController<T, ID> {

    protected final GenericService<T, ID> genericService;
    protected final Scanner scanner;

    public GenericController(GenericService<T, ID> genericService) {
        this.genericService = genericService;
        this.scanner = new Scanner(System.in);
    }

    public T save(T item) throws Exception {
        System.out.println("Tentando salvar item...");
        T createdItem = genericService.save(item);
        System.out.println("Item criado com sucesso!");
        return createdItem;
    }

    public Optional<T> getById(ID id) {
        System.out.println("Buscando item com ID: " + id);
        Optional<T> item = genericService.getById(id);
        if (item.isPresent()) {
            System.out.println("Item encontrado: " + item.get());
        } else {
            System.out.println("Item com ID " + id + " não encontrado.");
        }
        return item;
    }

    public List<T> getAll() {
        System.out.println("Buscando todos os itens...");
        List<T> items = genericService.getAll();
        if (items.isEmpty()) {
            System.out.println("Nenhum item encontrado.");
        } else {
            System.out.println("Itens encontrados:");
            items.forEach(System.out::println);
        }
        return items;
    }

    public T update(ID id, T updatedItem) throws Exception {
        System.out.println("Tentando atualizar item com ID: " + id);
        T item = genericService.update(id, updatedItem);
        System.out.println("Item atualizado com sucesso!");
        return item;
    }

    public boolean delete(ID id) throws RuntimeException {
        System.out.println("Tentando remover item com ID: " + id);
        boolean deleted = genericService.delete(id);
        if (deleted) {
            System.out.println("Item removido com sucesso!");
        } else {
            System.out.println("Item com ID " + id + " não encontrado para remoção.");
        }
        return deleted;
    }

    protected String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    protected int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            }
        }
    }
}