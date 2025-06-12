package com.br.ecoleta.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

    private JpaUtil() {
    }

    private static final EntityManagerFactory entityManagerFactory;

    static {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("ecocoleta-unit");
            ConsoleUtils.println("EntityManagerFactory inicializado com sucesso.");
        } catch (Exception e) {
            ConsoleUtils.printError("Erro ao inicializar EntityManagerFactory: " + e.getMessage());
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public static void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
            ConsoleUtils.println("EntityManagerFactory fechado.");
        }
    }
}