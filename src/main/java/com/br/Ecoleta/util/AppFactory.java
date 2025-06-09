package com.br.ecoleta.util;

import com.br.ecoleta.controller.*;
import com.br.ecoleta.repository.*;
import com.br.ecoleta.service.*;
import com.br.ecoleta.view.*;
import jakarta.persistence.EntityManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AppFactory {
    private final EntityManager entityManager;
    private final Scanner scanner;
    private final Map<Class<?>, Object> cache = new HashMap<>();

    public AppFactory(EntityManager entityManager, Scanner scanner) {
        this.entityManager = entityManager;
        this.scanner = scanner;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> clazz) {
        // Não usar get recursivo dentro do computeIfAbsent!
        if (cache.containsKey(clazz)) {
            return (T) cache.get(clazz);
        }
        Object instance;
        if (isRepository(clazz)) {
            instance = createRepository(clazz);
        } else if (isService(clazz)) {
            instance = createService(clazz);
        } else if (isController(clazz)) {
            instance = createController(clazz);
        } else if (isView(clazz)) {
            instance = createView(clazz);
        } else {
            throw new IllegalArgumentException("Classe não suportada: " + clazz);
        }
        cache.put(clazz, instance);
        return (T) instance;
    }

    private boolean isRepository(Class<?> c) {
        return c == ClienteRepository.class || c == PontoDeColetaRepository.class ||
               c == VeiculoRepository.class || c == MotoristaRepository.class ||
               c == RotaRepository.class || c == ColetaRepository.class;
    }

    private Object createRepository(Class<?> c) {
        if (c == ClienteRepository.class) return new ClienteRepository(entityManager);
        if (c == PontoDeColetaRepository.class) return new PontoDeColetaRepository(entityManager);
        if (c == VeiculoRepository.class) return new VeiculoRepository(entityManager);
        if (c == MotoristaRepository.class) return new MotoristaRepository(entityManager);
        if (c == RotaRepository.class) return new RotaRepository(entityManager);
        if (c == ColetaRepository.class) return new ColetaRepository(entityManager);
        throw new IllegalArgumentException("Repository não suportado: " + c);
    }

    private boolean isService(Class<?> c) {
        return c == ClienteService.class || c == PontoDeColetaService.class ||
               c == VeiculoService.class || c == MotoristaService.class ||
               c == RotaService.class || c == ColetaService.class;
    }

    private Object createService(Class<?> c) {
        if (c == ClienteService.class) return new ClienteService(get(ClienteRepository.class));
        if (c == PontoDeColetaService.class) return new PontoDeColetaService(get(PontoDeColetaRepository.class));
        if (c == VeiculoService.class) return new VeiculoService(get(VeiculoRepository.class));
        if (c == MotoristaService.class) return new MotoristaService(get(MotoristaRepository.class));
        if (c == RotaService.class) return new RotaService(get(RotaRepository.class));
        if (c == ColetaService.class) return new ColetaService(get(ColetaRepository.class));
        throw new IllegalArgumentException("Service não suportado: " + c);
    }

    private boolean isController(Class<?> c) {
        return c == ClienteController.class || c == PontoDeColetaController.class ||
               c == VeiculoController.class || c == MotoristaController.class ||
               c == RotaController.class || c == ColetaController.class;
    }

    private Object createController(Class<?> c) {
        if (c == ClienteController.class) return new ClienteController(get(ClienteService.class));
        if (c == PontoDeColetaController.class) return new PontoDeColetaController(get(PontoDeColetaService.class));
        if (c == VeiculoController.class) return new VeiculoController(get(VeiculoService.class));
        if (c == MotoristaController.class) return new MotoristaController(get(MotoristaService.class));
        if (c == RotaController.class) return new RotaController(get(RotaService.class));
        if (c == ColetaController.class) return new ColetaController(get(ColetaService.class));
        throw new IllegalArgumentException("Controller não suportado: " + c);
    }
    
    private boolean isView(Class<?> c) {
        return c == ClienteView.class || c == PontoDeColetaView.class ||
               c == VeiculoView.class || c == MotoristaView.class ||
               c == RotaView.class || c == ColetaView.class;
    }
    
    private Object createView(Class<?> c) {
        if (c == ClienteView.class) return new ClienteView(get(ClienteController.class), scanner);
        if (c == PontoDeColetaView.class) return new PontoDeColetaView(get(PontoDeColetaController.class), get(ClienteController.class), scanner);
        if (c == VeiculoView.class) return new VeiculoView(get(VeiculoController.class), scanner);
        if (c == MotoristaView.class) return new MotoristaView(get(MotoristaController.class), scanner);
        if (c == RotaView.class) return new RotaView(get(RotaController.class), get(MotoristaController.class), get(VeiculoController.class), scanner);
        if (c == ColetaView.class) return new ColetaView(get(ColetaController.class), get(ClienteController.class), get(PontoDeColetaController.class), get(RotaController.class), scanner);
        throw new IllegalArgumentException("View não suportada: " + c);
    }
}
