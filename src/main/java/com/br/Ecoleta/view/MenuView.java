package com.br.ecoleta.view;

import com.br.ecoleta.util.ConsoleUtils;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.InputMismatchException;

/**
 * Classe responsável por gerenciar o menu principal do sistema.
 * Utiliza as views específicas para cada funcionalidade.
 */
public class MenuView {
    private static final Logger logger = Logger.getLogger(MenuView.class.getName());
    private static final String MENU_HEADER = "\n=== SISTEMA ECOLETA - MENU PRINCIPAL ===";
    private static final String[] MENU_OPTIONS = {
        "1. Gerenciar Clientes",
        "2. Gerenciar Pontos de Coleta",
        "3. Gerenciar Veículos",
        "4. Gerenciar Motoristas",
        "5. Gerenciar Rotas",
        "6. Gerenciar Coletas",
        "0. Sair"
    };
    
    private final ClienteView clienteView;
    private final PontoDeColetaView pontoDeColetaView;
    private final VeiculoView veiculoView;
    private final MotoristaView motoristaView;
    private final RotaView rotaView;
    private final ColetaView coletaView;
    private final Scanner scanner;

    public MenuView(ClienteView clienteView, 
                   PontoDeColetaView pontoDeColetaView,
                   VeiculoView veiculoView,
                   MotoristaView motoristaView,
                   RotaView rotaView,
                   ColetaView coletaView,
                   Scanner scanner) {
        this.clienteView = clienteView;
        this.pontoDeColetaView = pontoDeColetaView;
        this.veiculoView = veiculoView;
        this.motoristaView = motoristaView;
        this.rotaView = rotaView;
        this.coletaView = coletaView;
        this.scanner = scanner;
        logger.fine("MenuView inicializado");
    }

    public void exibirMenuPrincipal() {
        logger.info("Iniciando execução do menu principal");
        int opcao;
        do {
            try {
                exibirOpcoes();
                opcao = lerOpcao();
                processarOpcao(opcao);
            } catch (Exception e) {
                logger.log(Level.WARNING, "Erro no menu principal", e);
                ConsoleUtils.printError("Erro ao processar opção. Tente novamente.");
                opcao = -1;
            }
        } while (opcao != 0);
        logger.info("Finalizando execução do menu principal");
    }

    private void exibirOpcoes() {
        ConsoleUtils.println(MENU_HEADER);
        ConsoleUtils.println("\nEscolha uma opção:");
        for (String option : MENU_OPTIONS) {
            ConsoleUtils.println(option);
        }
    }

    private int lerOpcao() {
        while (true) {
            try {
                ConsoleUtils.print("Opção: ");
                int opcao = scanner.nextInt();
                scanner.nextLine();
                logger.fine(() -> String.format("Opção selecionada: %d", opcao));
                return opcao;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                logger.warning("Entrada inválida fornecida pelo usuário");
                ConsoleUtils.printError("Por favor, digite um número válido.");
            }
        }
    }

    private void processarOpcao(int opcao) {
        try {
            switch (opcao) {
                case 1 -> {
                    logger.fine("Iniciando gestão de clientes");
                    clienteView.exibirMenuCliente();
                }
                case 2 -> {
                    logger.fine("Iniciando gestão de pontos de coleta");
                    pontoDeColetaView.exibirMenuPontoDeColeta();
                }
                case 3 -> {
                    logger.fine("Iniciando gestão de veículos");
                    veiculoView.exibirMenuVeiculo();
                }
                case 4 -> {
                    logger.fine("Iniciando gestão de motoristas");
                    motoristaView.exibirMenuMotorista();
                }
                case 5 -> {
                    logger.fine("Iniciando gestão de rotas");
                    rotaView.exibirMenuRota();
                }
                case 6 -> {
                    logger.fine("Iniciando gestão de coletas");
                    coletaView.exibirMenuColeta();
                }
                case 0 -> {
                    logger.info("Usuário solicitou encerramento");
                    ConsoleUtils.println("\nSaindo do sistema. Até mais!");
                }
                default -> {
                    if (logger.isLoggable(Level.WARNING)) {
                        logger.warning(String.format("Opção inválida selecionada: %d", opcao));
                    }
                    ConsoleUtils.printError("Opção inválida. Tente novamente.");
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao processar opção do menu", e);
            ConsoleUtils.printError("Erro inesperado. Por favor, tente novamente.");
        }
    }
}
