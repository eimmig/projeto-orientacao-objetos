package com.br.ecoleta.factory;

import com.br.ecoleta.controller.*;
import com.br.ecoleta.repository.*;
import com.br.ecoleta.service.*;
import com.br.ecoleta.view.*;
import jakarta.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AppFactory {
    private final Map<Class<?>, Object> instances;
    private final RepositoryFactory repositoryFactory;
    private final ServiceFactory serviceFactory;
    private final ControllerFactory controllerFactory;
    private final ViewFactory viewFactory;

    public AppFactory(EntityManager entityManager, Scanner scanner) {
        this.instances = new HashMap<>();
        
        this.repositoryFactory = RepositoryFactory.create(entityManager);
        this.serviceFactory = ServiceFactory.create(repositoryFactory);
        this.controllerFactory = ControllerFactory.create(serviceFactory);
        
        this.viewFactory = new ViewFactory.Builder()
            .scanner(scanner)
            .clienteController(controllerFactory.getController(ClienteController.class))
            .pontoDeColetaController(controllerFactory.getController(PontoDeColetaController.class))
            .veiculoController(controllerFactory.getController(VeiculoController.class))
            .motoristaController(controllerFactory.getController(MotoristaController.class))
            .rotaController(controllerFactory.getController(RotaController.class))
            .coletaController(controllerFactory.getController(ColetaController.class))
            .rotaService(serviceFactory.getService(RotaService.class))
            .build();
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> type) {
        if (instances.containsKey(type)) {
            return (T) instances.get(type);
        }

        Object instance = null;
        
        if (type == MenuView.class ||
            type == ClienteView.class || 
            type == PontoDeColetaView.class ||
            type == VeiculoView.class ||
            type == MotoristaView.class ||
            type == RotaView.class ||
            type == ColetaView.class) {
            instance = viewFactory.getView(type);
        }
        else if (type == ClienteController.class ||
                 type == PontoDeColetaController.class ||
                 type == VeiculoController.class ||
                 type == MotoristaController.class ||
                 type == RotaController.class ||
                 type == ColetaController.class) {
            instance = controllerFactory.getController(type);
        }
        else if (type == ClienteService.class ||
                 type == PontoDeColetaService.class ||
                 type == VeiculoService.class ||
                 type == MotoristaService.class ||
                 type == RotaService.class ||
                 type == ColetaService.class) {
            instance = serviceFactory.getService(type);
        }
        else if (type == ClienteRepository.class ||
                 type == PontoDeColetaRepository.class ||
                 type == VeiculoRepository.class ||
                 type == MotoristaRepository.class ||
                 type == RotaRepository.class ||
                 type == ColetaRepository.class) {
            instance = repositoryFactory.getRepository(type);
        }

        if (instance != null) {
            instances.put(type, instance);
            return (T) instance;
        }

        throw new IllegalArgumentException("Tipo não suportado: " + type.getName());
    }
}
