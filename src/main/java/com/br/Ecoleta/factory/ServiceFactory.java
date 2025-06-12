package com.br.ecoleta.factory;

import com.br.ecoleta.repository.*;
import com.br.ecoleta.service.*;
import java.util.HashMap;
import java.util.Map;

public class ServiceFactory {
    private final Map<Class<?>, Object> services;
    private final RepositoryFactory repositoryFactory;

    private ServiceFactory(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
        this.services = new HashMap<>();
        initializeServices();
    }

    private void initializeServices() {
        services.put(ClienteService.class, new ClienteService(repositoryFactory.getRepository(ClienteRepository.class)));        services.put(PontoDeColetaService.class, new PontoDeColetaService(repositoryFactory.getRepository(PontoDeColetaRepository.class)));
        services.put(VeiculoService.class, new VeiculoService(repositoryFactory.getRepository(VeiculoRepository.class)));
        services.put(MotoristaService.class, new MotoristaService(repositoryFactory.getRepository(MotoristaRepository.class)));
        services.put(ColetaService.class, new ColetaService(repositoryFactory.getRepository(ColetaRepository.class)));
        
        services.put(RotaService.class, new RotaService(
            repositoryFactory.getRepository(RotaRepository.class),
            (ColetaService) services.get(ColetaService.class)));
    }

    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> serviceClass) {
        return (T) services.get(serviceClass);
    }

    public static ServiceFactory create(RepositoryFactory repositoryFactory) {
        return new ServiceFactory(repositoryFactory);
    }
}
