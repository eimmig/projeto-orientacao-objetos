package com.br.ecoleta;

import com.br.ecoleta.util.AppFactory;
import com.br.ecoleta.util.JpaUtil;
import com.br.ecoleta.view.*;
import jakarta.persistence.EntityManager;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EntityManager entityManager = null;

        try {
            entityManager = JpaUtil.getEntityManager();
            AppFactory factory = new AppFactory(entityManager, scanner);

            ClienteView clienteView = factory.get(ClienteView.class);
            PontoDeColetaView pontoDeColetaView = factory.get(PontoDeColetaView.class);
            VeiculoView veiculoView = factory.get(VeiculoView.class);
            MotoristaView motoristaView = factory.get(MotoristaView.class);
            RotaView rotaView = factory.get(RotaView.class);
            ColetaView coletaView = factory.get(ColetaView.class);

            System.out.println("### Sistema Ecoleta - Menu Principal ###");

            int opcao;
            do {
                System.out.println("\nEscolha uma opção:");
                System.out.println("1. Gerenciar Clientes");
                System.out.println("2. Gerenciar Pontos de Coleta");
                System.out.println("3. Gerenciar Veículos");
                System.out.println("4. Gerenciar Motoristas");
                System.out.println("5. Gerenciar Rotas");
                System.out.println("6. Gerenciar Coletas");
                System.out.println("0. Sair");

                System.out.print("Opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        clienteView.exibirMenuCliente();
                        break;
                    case 2:
                        pontoDeColetaView.exibirMenuPontoDeColeta();
                        break;
                    case 3:
                        veiculoView.exibirMenuVeiculo();
                        break;
                    case 4:
                        motoristaView.exibirMenuMotorista();
                        break;
                    case 5:
                        rotaView.exibirMenuRota();
                        break;
                    case 6:
                        coletaView.exibirMenuColeta();
                        break;
                    case 0:
                        System.out.println("\nSaindo do sistema. Até mais!");
                        break;
                    default:
                        System.out.println("\nOpção inválida. Tente novamente.");
                }
            } while (opcao != 0);

        } catch (Exception e) {
            System.err.println("Ocorreu um erro crítico na aplicação: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
            JpaUtil.closeEntityManagerFactory();
            scanner.close();
        }
    }
}