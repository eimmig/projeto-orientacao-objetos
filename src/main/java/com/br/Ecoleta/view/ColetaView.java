package com.br.ecoleta.view;

import com.br.ecoleta.controller.ColetaController;
import com.br.ecoleta.controller.ClienteController;
import com.br.ecoleta.controller.PontoDeColetaController;
import com.br.ecoleta.controller.RotaController;
import com.br.ecoleta.model.Coleta;
import com.br.ecoleta.model.Cliente;
import com.br.ecoleta.model.PontoDeColeta;
import com.br.ecoleta.model.Rota;
import com.br.ecoleta.util.ColetaStatus;
import com.br.ecoleta.util.ConsoleUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

public class ColetaView {
    private static final String COLETA_COM_ID = "Coleta com ID ";
    private final ColetaController coletaController;
    private final ClienteController clienteController;
    private final PontoDeColetaController pontoDeColetaController;
    private final RotaController rotaController;
    private final Scanner scanner;

    public ColetaView(ColetaController coletaController, ClienteController clienteController, PontoDeColetaController pontoDeColetaController, RotaController rotaController, Scanner scanner) {
        this.coletaController = coletaController;
        this.clienteController = clienteController;
        this.pontoDeColetaController = pontoDeColetaController;
        this.rotaController = rotaController;
        this.scanner = scanner;
    }

    public void exibirMenuColeta() {
        int subOpcao;
        do {
            ConsoleUtils.println("\n--- Gerenciar Coletas ---");
            ConsoleUtils.println("1. Cadastrar Coleta");
            ConsoleUtils.println("2. Listar Todas as Coletas");
            ConsoleUtils.println("3. Buscar Coleta por ID");
            ConsoleUtils.println("4. Atualizar Coleta");
            ConsoleUtils.println("5. Excluir Coleta");
            ConsoleUtils.println("0. Voltar ao Menu Principal");

            ConsoleUtils.print("Opção");
            subOpcao = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (subOpcao) {
                    case 1:
                        cadastrarColeta();
                        break;
                    case 2:
                        listarTodasColetas();
                        break;
                    case 3:
                        buscarColetaPorId();
                        break;
                    case 4:
                        atualizarColeta();
                        break;
                    case 5:
                        excluirColeta();
                        break;
                    case 0:
                        ConsoleUtils.println("Voltando ao Menu Principal.");
                        break;
                    default:
                        ConsoleUtils.println("Opção inválida. Tente novamente.");
                }
            } catch (DateTimeParseException e) {
                ConsoleUtils.printError("Erro: Formato de data/hora inválido. Use YYYY-MM-DDTHH:MM:SS.");
            } catch (IllegalArgumentException e) {
                ConsoleUtils.printError("Erro: Status de coleta inválido. Por favor, use REALIZADA, PENDENTE ou CANCELADA.");
            } catch (Exception e) {
                ConsoleUtils.printError("Erro na operação de coletas: " + e.getMessage());
            }
        } while (subOpcao != 0);
    }

    private void cadastrarColeta() throws Exception {
        ConsoleUtils.println("\n--- Cadastro de Coleta ---");
        ConsoleUtils.print("Data e Hora da Coleta (YYYY-MM-DDTHH:MM:SS)");
        String dataHoraStr = scanner.nextLine();
        LocalDateTime dataHoraColeta = LocalDateTime.parse(dataHoraStr);
        ConsoleUtils.print("Quantidade (KG)");
        Double quantidadeKg = scanner.nextDouble();
        scanner.nextLine();
        ConsoleUtils.print("Observações (opcional)");
        String observacoes = scanner.nextLine();
        ConsoleUtils.print("Status (REALIZADA, PENDENTE, CANCELADA)");
        String statusStr = scanner.nextLine().toUpperCase();
        ColetaStatus status = ColetaStatus.valueOf(statusStr);
        ConsoleUtils.print("ID do Cliente");
        Long clienteId = scanner.nextLong();
        scanner.nextLine();
        ConsoleUtils.print("ID do Ponto de Coleta");
        Long pontoColetaId = scanner.nextLong();
        scanner.nextLine();
        ConsoleUtils.print("ID da Rota (Opcional, 0 se não houver)");
        Long rotaIdInput = scanner.nextLong();
        scanner.nextLine();
        Long rotaId = rotaIdInput == 0 ? null : rotaIdInput;
        Optional<Cliente> clienteOpt = clienteController.getById(clienteId);
        Optional<PontoDeColeta> pontoOpt = pontoDeColetaController.getById(pontoColetaId);
        Optional<Rota> rotaOpt = (rotaId != null) ? rotaController.getById(rotaId) : Optional.empty();
        if (clienteOpt.isPresent() && pontoOpt.isPresent() && (rotaId == null || rotaOpt.isPresent())) {
            Coleta novaColeta = new Coleta(dataHoraColeta, quantidadeKg, observacoes, status, clienteOpt.get(), pontoOpt.get(), rotaOpt.orElse(null));
            coletaController.save(novaColeta);
            ConsoleUtils.printSuccess("Coleta cadastrada com sucesso!");
        } else {
            if (clienteOpt.isEmpty()) {
                ConsoleUtils.printError("Cliente com ID " + clienteId + " não encontrado. Cadastro de coleta cancelado.");
            }
            if (pontoOpt.isEmpty()) {
                ConsoleUtils.printError("Ponto de Coleta com ID " + pontoColetaId + " não encontrado. Cadastro de coleta cancelado.");
            }
            if (rotaId != null && rotaOpt.isEmpty()) {
                ConsoleUtils.printError("Rota com ID " + rotaId + " não encontrada. Cadastro de coleta cancelado.");
            }
        }
    }

    private void listarTodasColetas() {
        ConsoleUtils.println("\n--- Todas as Coletas ---");
        var coletas = coletaController.getAll();
        if (coletas.isEmpty()) {
            ConsoleUtils.printInfo("Nenhuma coleta cadastrada no sistema.");
        } else {
            coletas.forEach(coleta -> ConsoleUtils.println(coleta.toString()));
        }
    }

    private void buscarColetaPorId() {
        ConsoleUtils.println("\n--- Buscar Coleta por ID ---");
        ConsoleUtils.print("Digite o ID da coleta");
        Long idBusca = scanner.nextLong();
        scanner.nextLine();
        Optional<Coleta> coletaOpt = coletaController.getById(idBusca);
        if (coletaOpt.isPresent()) {
            ConsoleUtils.printDivider();
            ConsoleUtils.println(coletaOpt.get().toString());
            ConsoleUtils.printDivider();
            ConsoleUtils.printError(COLETA_COM_ID + idBusca + " não encontrada.");
        }
    }

    private void atualizarColeta() throws Exception {
        ConsoleUtils.println("\n--- Atualizar Coleta ---");
        ConsoleUtils.print("Digite o ID da coleta a ser atualizada");
        Long idUpdate = scanner.nextLong();
        scanner.nextLine();
        Optional<Coleta> coletaExistenteOpt = coletaController.getById(idUpdate);
        if (coletaExistenteOpt.isPresent()) {
            Coleta coletaExistente = coletaExistenteOpt.get();
            ConsoleUtils.println("Coleta encontrada. Digite os novos dados (deixe em branco para manter o atual):");
            atualizarDataHoraColeta(coletaExistente);
            atualizarQuantidadeKg(coletaExistente);
            atualizarObservacoes(coletaExistente);
            atualizarStatus(coletaExistente);
            atualizarCliente(coletaExistente);
            atualizarPontoDeColeta(coletaExistente);
            atualizarRota(coletaExistente);
            coletaController.update(idUpdate, coletaExistente);
            ConsoleUtils.printSuccess("Coleta atualizada com sucesso!");
            ConsoleUtils.printError(COLETA_COM_ID + idUpdate + " não encontrada. Não foi possível atualizar.");
        }
    }

    private void atualizarDataHoraColeta(Coleta coleta) {
        ConsoleUtils.print("Nova Data e Hora (" + coleta.getDataHoraColeta() + ") (YYYY-MM-DDTHH:MM:SS)");
        String novaDataHoraStr = scanner.nextLine();
        if (!novaDataHoraStr.trim().isEmpty()) {
            coleta.setDataHoraColeta(LocalDateTime.parse(novaDataHoraStr));
        }
    }

    private void atualizarQuantidadeKg(Coleta coleta) {
        ConsoleUtils.print("Nova Quantidade (KG) (" + coleta.getQuantidadeKg() + ")");
        String novaQuantidadeStr = scanner.nextLine();
        if (!novaQuantidadeStr.trim().isEmpty()) {
            coleta.setQuantidadeKg(Double.parseDouble(novaQuantidadeStr));
        }
    }

    private void atualizarObservacoes(Coleta coleta) {
        ConsoleUtils.print("Novas Observações (" + coleta.getObservacoes() + ")");
        String novasObservacoes = scanner.nextLine();
        if (!novasObservacoes.trim().isEmpty()) {
            coleta.setObservacoes(novasObservacoes);
        }
    }

    private void atualizarStatus(Coleta coleta) {
        ConsoleUtils.print("Novo Status (" + coleta.getStatus() + ") (REALIZADA, PENDENTE, CANCELADA)");
        String novoStatusStr = scanner.nextLine().toUpperCase();
        if (!novoStatusStr.trim().isEmpty()) {
            coleta.setStatus(ColetaStatus.valueOf(novoStatusStr));
        }
    }

    private void atualizarCliente(Coleta coleta) {
        ConsoleUtils.print("Novo ID do Cliente (" + coleta.getCliente().getId() + ")");
        String novoClienteIdStr = scanner.nextLine();
        if (!novoClienteIdStr.trim().isEmpty()) {
            Long novoClienteId = Long.parseLong(novoClienteIdStr);
            Optional<Cliente> novoClienteOpt = clienteController.getById(novoClienteId);
            if (novoClienteOpt.isPresent()) {
                coleta.setCliente(novoClienteOpt.get());
            } else {
                ConsoleUtils.println("Cliente com ID " + novoClienteId + " não encontrado. Cliente não será atualizado.");
            }
        }
    }

    private void atualizarPontoDeColeta(Coleta coleta) {
        ConsoleUtils.print("Novo ID do Ponto de Coleta (" + coleta.getPontoDeColeta().getId() + ")");
        String novoPontoIdStr = scanner.nextLine();
        if (!novoPontoIdStr.trim().isEmpty()) {
            Long novoPontoId = Long.parseLong(novoPontoIdStr);
            Optional<PontoDeColeta> novoPontoOpt = pontoDeColetaController.getById(novoPontoId);
            if (novoPontoOpt.isPresent()) {
                coleta.setPontoDeColeta(novoPontoOpt.get());
            } else {
                ConsoleUtils.println("Ponto de Coleta com ID " + novoPontoId + " não encontrado. Ponto não será atualizado.");
            }
        }
    }

    private void atualizarRota(Coleta coleta) {
        ConsoleUtils.print("Novo ID da Rota (" + (coleta.getRota() != null ? coleta.getRota().getId() : "null") + ") (Opcional, 0 se não houver)");
        String novaRotaIdStr = scanner.nextLine();
        if (!novaRotaIdStr.trim().isEmpty()) {
            Long novaRotaId = Long.parseLong(novaRotaIdStr);
            if (novaRotaId == 0) {
                coleta.setRota(null);
            } else {
                Optional<Rota> novaRotaOpt = rotaController.getById(novaRotaId);
                if (novaRotaOpt.isPresent()) {
                    coleta.setRota(novaRotaOpt.get());
                } else {
                    ConsoleUtils.println("Rota com ID " + novaRotaId + " não encontrada. Rota não será atualizada.");
                }
            }
        }
    }

    private void excluirColeta() {
        ConsoleUtils.println("\n--- Excluir Coleta ---");
        ConsoleUtils.print("Digite o ID da coleta a ser excluída");
        Long idDelete = scanner.nextLong();
        scanner.nextLine();
        Optional<Coleta> coletaOpt = coletaController.getById(idDelete);
        if (coletaOpt.isPresent()) {
            if (coletaController.delete(idDelete)) {
                ConsoleUtils.printSuccess("Coleta excluída com sucesso!");
            } else {
                ConsoleUtils.printError("Não foi possível excluir a coleta. Tente novamente ou verifique se a coleta está vinculada a outros registros.");
            }
            ConsoleUtils.printError(COLETA_COM_ID + idDelete + " não encontrada. Não foi possível excluir.");
        }
    }
}