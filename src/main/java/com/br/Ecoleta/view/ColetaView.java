package com.br.Ecoleta.view;

import com.br.Ecoleta.controller.ColetaController;
import com.br.Ecoleta.controller.ClienteController;
import com.br.Ecoleta.controller.PontoDeColetaController;
import com.br.Ecoleta.controller.RotaController;
import com.br.Ecoleta.model.Coleta;
import com.br.Ecoleta.model.Cliente;
import com.br.Ecoleta.model.PontoDeColeta;
import com.br.Ecoleta.model.Rota;
import com.br.Ecoleta.util.ColetaStatus;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

public class ColetaView {
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
            System.out.println("\n--- Gerenciar Coletas ---");
            System.out.println("1. Cadastrar Coleta");
            System.out.println("2. Listar Todas as Coletas");
            System.out.println("3. Buscar Coleta por ID");
            System.out.println("4. Atualizar Coleta");
            System.out.println("5. Excluir Coleta");
            System.out.println("0. Voltar ao Menu Principal");

            System.out.print("Opção: ");
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
                        System.out.println("Voltando ao Menu Principal.");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (DateTimeParseException e) {
                System.err.println("Erro: Formato de data/hora inválido. Use YYYY-MM-DDTHH:MM:SS.");
            } catch (IllegalArgumentException e) {
                System.err.println("Erro: Status de coleta inválido. Por favor, use REALIZADA, PENDENTE ou CANCELADA.");
            } catch (Exception e) {
                System.err.println("Erro na operação de coletas: " + e.getMessage());
            }
        } while (subOpcao != 0);
    }

    private void cadastrarColeta() throws Exception {
        System.out.println("\n--- Cadastro de Coleta ---");
        System.out.print("Data e Hora da Coleta (YYYY-MM-DDTHH:MM:SS): ");
        String dataHoraStr = scanner.nextLine();
        LocalDateTime dataHoraColeta = LocalDateTime.parse(dataHoraStr);

        System.out.print("Quantidade (KG): ");
        Double quantidadeKg = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Observações (opcional): ");
        String observacoes = scanner.nextLine();

        System.out.print("Status (REALIZADA, PENDENTE, CANCELADA): ");
        String statusStr = scanner.nextLine().toUpperCase();
        ColetaStatus status = ColetaStatus.valueOf(statusStr);

        System.out.print("ID do Cliente: ");
        Long clienteId = scanner.nextLong();
        scanner.nextLine();

        System.out.print("ID do Ponto de Coleta: ");
        Long pontoColetaId = scanner.nextLong();
        scanner.nextLine();

        System.out.print("ID da Rota (Opcional, 0 se não houver): ");
        Long rotaIdInput = scanner.nextLong();
        scanner.nextLine();
        Long rotaId = rotaIdInput == 0 ? null : rotaIdInput;

        Optional<Cliente> clienteOpt = clienteController.getById(clienteId);
        Optional<PontoDeColeta> pontoOpt = pontoDeColetaController.getById(pontoColetaId);
        Optional<Rota> rotaOpt = (rotaId != null) ? rotaController.getById(rotaId) : Optional.empty();

        if (clienteOpt.isPresent() && pontoOpt.isPresent() && (rotaId == null || rotaOpt.isPresent())) {
            Coleta novaColeta = new Coleta(dataHoraColeta, quantidadeKg, observacoes, status, clienteOpt.get(), pontoOpt.get(), rotaOpt.orElse(null));
            coletaController.save(novaColeta);
        } else {
            System.out.println("Cliente, Ponto de Coleta ou Rota não encontrado. Operação cancelada.");
        }
    }

    private void listarTodasColetas() {
        System.out.println("\n--- Todas as Coletas ---");
        coletaController.getAll();
    }

    private void buscarColetaPorId() {
        System.out.println("\n--- Buscar Coleta por ID ---");
        System.out.print("Digite o ID da coleta: ");
        Long idBusca = scanner.nextLong();
        scanner.nextLine();
        coletaController.getById(idBusca);
    }

    private void atualizarColeta() throws Exception {
        System.out.println("\n--- Atualizar Coleta ---");
        System.out.print("Digite o ID da coleta a ser atualizada: ");
        Long idUpdate = scanner.nextLong();
        scanner.nextLine();

        Optional<Coleta> coletaExistenteOpt = coletaController.getById(idUpdate);
        if (coletaExistenteOpt.isPresent()) {
            Coleta coletaExistente = coletaExistenteOpt.get();
            System.out.println("Coleta encontrada. Digite os novos dados (deixe em branco para manter o atual):");

            System.out.print("Nova Data e Hora (" + coletaExistente.getDataHoraColeta() + ") (YYYY-MM-DDTHH:MM:SS): ");
            String novaDataHoraStr = scanner.nextLine();
            if (!novaDataHoraStr.trim().isEmpty()) coletaExistente.setDataHoraColeta(LocalDateTime.parse(novaDataHoraStr));

            System.out.print("Nova Quantidade (KG) (" + coletaExistente.getQuantidadeKg() + "): ");
            String novaQuantStr = scanner.nextLine();
            if (!novaQuantStr.trim().isEmpty()) coletaExistente.setQuantidadeKg(Double.parseDouble(novaQuantStr));

            System.out.print("Novas Observações (" + coletaExistente.getObservacoes() + "): ");
            String novasObs = scanner.nextLine();
            if (!novasObs.trim().isEmpty()) coletaExistente.setObservacoes(novasObs);

            System.out.print("Novo Status (" + coletaExistente.getStatus() + ") (REALIZADA, PENDENTE, CANCELADA): ");
            String novoStatusStr = scanner.nextLine().toUpperCase();
            if (!novoStatusStr.trim().isEmpty()) coletaExistente.setStatus(ColetaStatus.valueOf(novoStatusStr));

            System.out.print("Novo ID do Cliente (" + (coletaExistente.getCliente() != null ? coletaExistente.getCliente().getId() : "null") + "): ");
            String novoClienteIdStr = scanner.nextLine();
            if (!novoClienteIdStr.trim().isEmpty()) {
                Long novoClienteId = Long.parseLong(novoClienteIdStr);
                Optional<Cliente> novoClienteOpt = clienteController.getById(novoClienteId);
                if (novoClienteOpt.isPresent()) {
                    coletaExistente.setCliente(novoClienteOpt.get());
                } else {
                    System.out.println("Cliente com ID " + novoClienteId + " não encontrado. Cliente não será atualizado.");
                }
            }

            System.out.print("Novo ID do Ponto de Coleta (" + (coletaExistente.getPontoDeColeta() != null ? coletaExistente.getPontoDeColeta().getId() : "null") + "): ");
            String novoPontoIdStr = scanner.nextLine();
            if (!novoPontoIdStr.trim().isEmpty()) {
                Long novoPontoId = Long.parseLong(novoPontoIdStr);
                Optional<PontoDeColeta> novoPontoOpt = pontoDeColetaController.getById(novoPontoId);
                if (novoPontoOpt.isPresent()) {
                    coletaExistente.setPontoDeColeta(novoPontoOpt.get());
                } else {
                    System.out.println("Ponto de Coleta com ID " + novoPontoId + " não encontrado. Ponto de Coleta não será atualizado.");
                }
            }

            System.out.print("Novo ID da Rota (" + (coletaExistente.getRota() != null ? coletaExistente.getRota().getId() : "null") + ") (0 para remover): ");
            String novaRotaIdStr = scanner.nextLine();
            if (!novaRotaIdStr.trim().isEmpty()) {
                Long novaRotaId = Long.parseLong(novaRotaIdStr);
                if (novaRotaId == 0) {
                    coletaExistente.setRota(null);
                } else {
                    Optional<Rota> novaRotaOpt = rotaController.getById(novaRotaId);
                    if (novaRotaOpt.isPresent()) {
                        coletaExistente.setRota(novaRotaOpt.get());
                    } else {
                        System.out.println("Rota com ID " + novaRotaId + " não encontrada. Rota não será atualizada.");
                    }
                }
            }

            coletaController.update(idUpdate, coletaExistente);
        } else {
            System.out.println("Coleta com ID " + idUpdate + " não encontrada.");
        }
    }

    private void excluirColeta() {
        System.out.println("\n--- Excluir Coleta ---");
        System.out.print("Digite o ID da coleta a ser excluída: ");
        Long idDelete = scanner.nextLong();
        scanner.nextLine();
        coletaController.delete(idDelete);
    }
}