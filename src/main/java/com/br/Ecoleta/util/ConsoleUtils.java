package com.br.ecoleta.util;

/**
 * Classe utilitária para gerenciar a saída do console de forma centralizada.
 * Separa a lógica de apresentação da lógica de logging do sistema.
 */
public class ConsoleUtils {
    private ConsoleUtils() {
    }

    public static void println(String message) {
        System.out.println(message);
    }

    public static void print(String message) {
        System.out.print(message + ": ");
    }

    public static void printError(String message) {
        System.err.println("\nERRO: " + message);
    }

    public static void printSuccess(String message) {
        System.out.println("\nSUCESSO: " + message);
    }

    public static void printInfo(String message) {
        System.out.println("\nINFO: " + message);
    }

    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printDivider() {
        System.out.println("----------------------------------------");
    }
}
