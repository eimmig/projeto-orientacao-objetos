package com.br.ecoleta.factory;

import com.br.ecoleta.controller.*;
import com.br.ecoleta.service.*;
import java.util.HashMap;
import java.util.Map;

public class ControllerFactory {
    private final Map<Class<?>, Object> controllers;
    private final ServiceFactory serviceFactory;

    private ControllerFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
        this.controllers = new HashMap<>();
        initializeControllers();
    }

    private void initializeControllers() {
        controllers.put(ClienteController.class, new ClienteController(serviceFactory.getService(ClienteService.class)));
        controllers.put(PontoDeColetaController.class, new PontoDeColetaController(serviceFactory.getService(PontoDeColetaService.class)));
        controllers.put(VeiculoController.class, new VeiculoController(serviceFactory.getService(VeiculoService.class)));
        controllers.put(MotoristaController.class, new MotoristaController(serviceFactory.getService(MotoristaService.class)));
        controllers.put(RotaController.class, new RotaController(serviceFactory.getService(RotaService.class)));
        controllers.put(ColetaController.class, new ColetaController(serviceFactory.getService(ColetaService.class)));
    }

    @SuppressWarnings("unchecked")
    public <T> T getController(Class<T> controllerClass) {
        return (T) controllers.get(controllerClass);
    }

    public static ControllerFactory create(ServiceFactory serviceFactory) {
        return new ControllerFactory(serviceFactory);
    }
}
