package com.br.ecoleta.factory;

import com.br.ecoleta.repository.*;
import jakarta.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

public class RepositoryFactory {
    private final Map<Class<?>, Object> repositories;
    private final EntityManager entityManager;

    private RepositoryFactory(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.repositories = new HashMap<>();
        initializeRepositories();
    }

    private void initializeRepositories() {
        repositories.put(ClienteRepository.class, new ClienteRepository(entityManager));
        repositories.put(PontoDeColetaRepository.class, new PontoDeColetaRepository(entityManager));
        repositories.put(VeiculoRepository.class, new VeiculoRepository(entityManager));
        repositories.put(MotoristaRepository.class, new MotoristaRepository(entityManager));
        repositories.put(RotaRepository.class, new RotaRepository(entityManager));
        repositories.put(ColetaRepository.class, new ColetaRepository(entityManager));
    }

    @SuppressWarnings("unchecked")
    public <T> T getRepository(Class<T> repositoryClass) {
        return (T) repositories.get(repositoryClass);
    }

    public static RepositoryFactory create(EntityManager entityManager) {
        return new RepositoryFactory(entityManager);
    }
}
