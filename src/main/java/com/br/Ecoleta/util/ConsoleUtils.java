package com.br.ecoleta.util;

import java.util.logging.Logger;

/**
 * Classe utilitária para gerenciar a saída do console de forma centralizada.
 * Separa a lógica de apresentação da lógica de logging do sistema.
 */
public class ConsoleUtils {
    private ConsoleUtils() {
    }

    private static final Logger logger = Logger.getLogger(ConsoleUtils.class.getName());

    public static void println(String message) {
        logger.info(message);
    }

    public static void print(String message) {
        logger.info(message + ": ");
    }

    public static void printError(String message) {
        logger.severe("ERRO: " + message);
    }

    public static void printSuccess(String message) {
        logger.info("SUCESSO: " + message);
    }

    public static void printInfo(String message) {
        logger.info("INFO: " + message);
    }

    public static void clear() {
        // Limpeza de tela não é suportada via logger, mas mantemos para compatibilidade
        System.out.print("\033[H\033[2J");
        System.out.flush();
        logger.fine("Tela limpa pelo usuário.");
    }

    public static void printDivider() {
        logger.info("----------------------------------------");
    }
}
