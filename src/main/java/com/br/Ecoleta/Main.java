package com.br.Ecoleta;

import com.br.Ecoleta.controller.*;
import com.br.Ecoleta.repository.*;
import com.br.Ecoleta.service.*;
import com.br.Ecoleta.util.JpaUtil;
import com.br.Ecoleta.view.*; // Importa o novo pacote de views
import jakarta.persistence.EntityManager;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EntityManager entityManager = null;

        try {
            entityManager = JpaUtil.getEntityManager();

            ClienteRepository clienteRepository = new ClienteRepository(entityManager);
            PontoDeColetaRepository pontoDeColetaRepository = new PontoDeColetaRepository(entityManager);
            VeiculoRepository veiculoRepository = new VeiculoRepository(entityManager);
            MotoristaRepository motoristaRepository = new MotoristaRepository(entityManager);
            RotaRepository rotaRepository = new RotaRepository(entityManager);
            ColetaRepository coletaRepository = new ColetaRepository(entityManager);

            ClienteService clienteService = new ClienteService(clienteRepository);
            PontoDeColetaService pontoDeColetaService = new PontoDeColetaService(pontoDeColetaRepository);
            VeiculoService veiculoService = new VeiculoService(veiculoRepository);
            MotoristaService motoristaService = new MotoristaService(motoristaRepository);
            RotaService rotaService = new RotaService(rotaRepository);
            ColetaService coletaService = new ColetaService(coletaRepository);

            ClienteController clienteController = new ClienteController(clienteService);
            PontoDeColetaController pontoDeColetaController = new PontoDeColetaController(pontoDeColetaService);
            VeiculoController veiculoController = new VeiculoController(veiculoService);
            MotoristaController motoristaController = new MotoristaController(motoristaService);
            RotaController rotaController = new RotaController(rotaService);
            ColetaController coletaController = new ColetaController(coletaService);

            ClienteView clienteView = new ClienteView(clienteController, scanner);
            PontoDeColetaView pontoDeColetaView = new PontoDeColetaView(pontoDeColetaController, clienteController, scanner);
            VeiculoView veiculoView = new VeiculoView(veiculoController, scanner);
            MotoristaView motoristaView = new MotoristaView(motoristaController, scanner);
            RotaView rotaView = new RotaView(rotaController, motoristaController, veiculoController, scanner);
            ColetaView coletaView = new ColetaView(coletaController, clienteController, pontoDeColetaController, rotaController, scanner);

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