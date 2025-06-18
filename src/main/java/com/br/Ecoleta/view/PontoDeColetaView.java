package com.br.ecoleta.view;

import com.br.ecoleta.controller.ClienteController;
import com.br.ecoleta.controller.PontoDeColetaController;
import com.br.ecoleta.exception.PontoDeColetaException;
import com.br.ecoleta.model.Cliente;
import com.br.ecoleta.model.PontoDeColeta;
import com.br.ecoleta.util.ConsoleUtils;
import java.util.Optional;
import java.util.Scanner;

public class PontoDeColetaView {
    private static final String PONTO_COLETA_ID_MSG = "Ponto de Coleta com ID ";
    private final PontoDeColetaController pontoDeColetaController;
    private final ClienteController clienteController;
    private final Scanner scanner;

    public PontoDeColetaView(PontoDeColetaController pontoDeColetaController, ClienteController clienteController, Scanner scanner) {
        this.pontoDeColetaController = pontoDeColetaController;
        this.clienteController = clienteController;
        this.scanner = scanner;
    }

    public void exibirMenuPontoDeColeta() {
        int subOpcao;
        do {
            ConsoleUtils.println("\n--- Gerenciar Pontos de Coleta ---");
            ConsoleUtils.println("1. Cadastrar Ponto de Coleta");
            ConsoleUtils.println("2. Listar Todos os Pontos de Coleta");
            ConsoleUtils.println("3. Buscar Ponto de Coleta por ID");
            ConsoleUtils.println("4. Atualizar Ponto de Coleta");
            ConsoleUtils.println("5. Excluir Ponto de Coleta");
            ConsoleUtils.println("0. Voltar ao Menu Principal");

            ConsoleUtils.print("Opção");
            subOpcao = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (subOpcao) {
                    case 1:
                        cadastrarPontoDeColeta();
                        break;
                    case 2:
                        listarTodosPontosDeColeta();
                        break;
                    case 3:
                        buscarPontoDeColetaPorId();
                        break;
                    case 4:
                        atualizarPontoDeColeta();
                        break;
                    case 5:
                        excluirPontoDeColeta();
                        break;
                    case 0:
                        ConsoleUtils.println("Voltando ao Menu Principal.");
                        break;
                    default:
                        ConsoleUtils.println("Opção inválida. Tente novamente.");
                }
            } catch (PontoDeColetaException e) {
                ConsoleUtils.printError("Erro de ponto de coleta: " + e.getMessage());
            } catch (Exception e) {
                ConsoleUtils.printError("Erro inesperado: " + e.getMessage());
            }
        } while (subOpcao != 0);
    }

    private void cadastrarPontoDeColeta() {
        ConsoleUtils.println("\n--- Cadastro de Ponto de Coleta ---");
        ConsoleUtils.print("Nome do Local");
        String nomeLocal = scanner.nextLine();
        if (nomeLocal.trim().isEmpty()) {
            ConsoleUtils.printError("O nome do local não pode estar vazio. Por favor, preencha corretamente.");
            throw new PontoDeColetaException("Nome do local não pode estar vazio");
        }
        ConsoleUtils.print("Endereço");
        String endereco = scanner.nextLine();
        if (endereco.trim().isEmpty()) {
            ConsoleUtils.printError("O endereço não pode estar vazio. Por favor, preencha corretamente.");
            throw new PontoDeColetaException("Endereço não pode estar vazio");
        }
        ConsoleUtils.print("Latitude");
        Double latitude = scanner.nextDouble();
        ConsoleUtils.print("Longitude");
        Double longitude = scanner.nextDouble();
        scanner.nextLine();
        ConsoleUtils.print("ID do Cliente responsável");
        Long clienteId = scanner.nextLong();
        scanner.nextLine();
        Optional<Cliente> clienteOpt = clienteController.getById(clienteId);
        if (clienteOpt.isPresent()) {
            PontoDeColeta novoPonto = new PontoDeColeta(nomeLocal, endereco, latitude, longitude, clienteOpt.get());
            pontoDeColetaController.save(novoPonto);
            ConsoleUtils.printSuccess("Ponto de Coleta cadastrado com sucesso!");
        } else {
            ConsoleUtils.printError("Cliente com ID " + clienteId + " não encontrado. Cadastro de ponto de coleta cancelado.");
            throw new PontoDeColetaException("Cliente não encontrado para o ponto de coleta");
        }
    }

    private void listarTodosPontosDeColeta() {
        ConsoleUtils.println("\n--- Todos os Pontos de Coleta ---");
        var pontos = pontoDeColetaController.getAll();
        if (pontos.isEmpty()) {
            ConsoleUtils.printInfo("Nenhum ponto de coleta cadastrado no sistema.");
        } else {
            pontos.forEach(ponto -> ConsoleUtils.println(ponto.toString()));
        }
    }

    private void buscarPontoDeColetaPorId() {
        ConsoleUtils.println("\n--- Buscar Ponto de Coleta por ID ---");
        ConsoleUtils.print("Digite o ID do ponto de coleta");
        Long idBusca = scanner.nextLong();
        scanner.nextLine();
        Optional<PontoDeColeta> pontoOpt = pontoDeColetaController.getById(idBusca);
        if (pontoOpt.isPresent()) {
            ConsoleUtils.printDivider();
            ConsoleUtils.println(pontoOpt.get().toString());
            ConsoleUtils.printDivider();
            ConsoleUtils.printError(PONTO_COLETA_ID_MSG + idBusca + " não encontrado.");
        }
    }

    private void atualizarPontoDeColeta() {
        ConsoleUtils.println("\n--- Atualizar Ponto de Coleta ---");
        ConsoleUtils.print("Digite o ID do ponto de coleta a ser atualizado");
        Long idUpdate = scanner.nextLong();
        scanner.nextLine();
        Optional<PontoDeColeta> pontoExistenteOpt = pontoDeColetaController.getById(idUpdate);
        if (pontoExistenteOpt.isPresent()) {
            PontoDeColeta pontoExistente = pontoExistenteOpt.get();
            ConsoleUtils.println("Ponto de Coleta encontrado. Digite os novos dados (deixe em branco para manter o atual):");

            atualizarNomeLocal(pontoExistente);
            atualizarEndereco(pontoExistente);
            atualizarLatitude(pontoExistente);
            atualizarLongitude(pontoExistente);
            atualizarCliente(pontoExistente);

            pontoDeColetaController.update(idUpdate, pontoExistente);
            ConsoleUtils.printSuccess("Ponto de Coleta atualizado com sucesso!");
            ConsoleUtils.printError(PONTO_COLETA_ID_MSG + idUpdate + " não encontrado. Não foi possível atualizar.");
        }
    }

    private void atualizarNomeLocal(PontoDeColeta ponto) {
        ConsoleUtils.print("Novo Nome do Local (" + ponto.getNomeLocal() + ")");
        String novoNomeLocal = scanner.nextLine();
        if (!novoNomeLocal.trim().isEmpty()) {
            ponto.setNomeLocal(novoNomeLocal);
        }
    }

    private void atualizarEndereco(PontoDeColeta ponto) {
        ConsoleUtils.print("Novo Endereço (" + ponto.getEndereco() + ")");
        String novoEndereco = scanner.nextLine();
        if (!novoEndereco.trim().isEmpty()) {
            ponto.setEndereco(novoEndereco);
        }
    }

    private void atualizarLatitude(PontoDeColeta ponto) {
        ConsoleUtils.print("Nova Latitude (" + ponto.getLatitude() + ")");
        String latStr = scanner.nextLine();
        if (!latStr.trim().isEmpty()) {
            ponto.setLatitude(Double.parseDouble(latStr));
        }
    }

    private void atualizarLongitude(PontoDeColeta ponto) {
        ConsoleUtils.print("Nova Longitude (" + ponto.getLongitude() + ")");
        String longStr = scanner.nextLine();
        if (!longStr.trim().isEmpty()) {
            ponto.setLongitude(Double.parseDouble(longStr));
        }
    }

    private void atualizarCliente(PontoDeColeta ponto) {
        ConsoleUtils.print("Novo ID do Cliente (" + ponto.getCliente().getId() + ")");
        String clienteIdStr = scanner.nextLine();
        if (!clienteIdStr.trim().isEmpty()) {
            Long novoClienteId = Long.parseLong(clienteIdStr);
            Optional<Cliente> novoClienteOpt = clienteController.getById(novoClienteId);
            if (novoClienteOpt.isPresent()) {
                ponto.setCliente(novoClienteOpt.get());
            } else {
                ConsoleUtils.println("Cliente com ID " + novoClienteId + " não encontrado. Cliente não será atualizado.");
            }
        }
    }

    private void excluirPontoDeColeta() {
        ConsoleUtils.println("\n--- Excluir Ponto de Coleta ---");
        ConsoleUtils.print("Digite o ID do ponto de coleta a ser excluído");
        Long idDelete = scanner.nextLong();
        scanner.nextLine();
        Optional<PontoDeColeta> pontoOpt = pontoDeColetaController.getById(idDelete);
        if (pontoOpt.isPresent()) {
            if (pontoDeColetaController.delete(idDelete)) {
                ConsoleUtils.printSuccess("Ponto de Coleta excluído com sucesso!");
            } else {
                ConsoleUtils.printError("Não foi possível excluir o ponto de coleta. Tente novamente ou verifique se o ponto está vinculado a outros registros.");
            }
            ConsoleUtils.printError(PONTO_COLETA_ID_MSG + idDelete + " não encontrado. Não foi possível excluir.");
        }
    }
}