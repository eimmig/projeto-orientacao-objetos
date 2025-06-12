package com.br.ecoleta;

import com.br.ecoleta.factory.AppFactory;
import com.br.ecoleta.util.JpaUtil;
import com.br.ecoleta.view.*;
import jakarta.persistence.EntityManager;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EntityManager entityManager = null;

        try {
            entityManager = JpaUtil.getEntityManager();
            AppFactory factory = new AppFactory(entityManager, scanner);

            MenuView menuView = new MenuView(
                factory.get(ClienteView.class),
                factory.get(PontoDeColetaView.class),
                factory.get(VeiculoView.class),
                factory.get(MotoristaView.class),
                factory.get(RotaView.class),
                factory.get(ColetaView.class),
                scanner
            );

            menuView.exibirMenuPrincipal();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ocorreu um erro crítico na aplicação", e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
            JpaUtil.closeEntityManagerFactory();
            scanner.close();
        }
    }
}