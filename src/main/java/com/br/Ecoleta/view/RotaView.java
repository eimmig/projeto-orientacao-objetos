package com.br.ecoleta.view;

import com.br.ecoleta.controller.MotoristaController;
import com.br.ecoleta.controller.RotaController;
import com.br.ecoleta.controller.VeiculoController;
import com.br.ecoleta.model.Motorista;
import com.br.ecoleta.model.Rota;
import com.br.ecoleta.model.Veiculo;
import com.br.ecoleta.util.ConsoleUtils;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

public class RotaView {
    private static final String ROTA_COM_ID = "Rota com ID ";
    private final RotaController rotaController;
    private final MotoristaController motoristaController;
    private final VeiculoController veiculoController;
    private final Scanner scanner;

    public RotaView(RotaController rotaController, MotoristaController motoristaController, VeiculoController veiculoController, Scanner scanner) {
        this.rotaController = rotaController;
        this.motoristaController = motoristaController;
        this.veiculoController = veiculoController;
        this.scanner = scanner;
    }

    public void exibirMenuRota() {
        int subOpcao;
        do {
            ConsoleUtils.println("\n--- Gerenciar Rotas ---");
            ConsoleUtils.println("1. Cadastrar Rota");
            ConsoleUtils.println("2. Listar Todas as Rotas");
            ConsoleUtils.println("3. Buscar Rota por ID");
            ConsoleUtils.println("4. Atualizar Rota");
            ConsoleUtils.println("5. Excluir Rota");
            ConsoleUtils.println("6. Listar Coletas Pendentes de uma Rota");
            ConsoleUtils.println("0. Voltar ao Menu Principal");

            ConsoleUtils.print("Opção");
            subOpcao = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (subOpcao) {
                    case 1:
                        cadastrarRota();
                        break;
                    case 2:
                        listarTodasRotas();
                        break;
                    case 3:
                        buscarRotaPorId();
                        break;
                    case 4:
                        atualizarRota();
                        break;
                    case 5:
                        excluirRota();
                        break;
                    case 6:
                        listarColetasPendentesDaRota();
                        break;
                    case 0:
                        ConsoleUtils.println("Voltando ao Menu Principal.");
                        break;
                    default:
                        ConsoleUtils.println("Opção inválida. Tente novamente.");
                }
            } catch (DateTimeParseException e) {
                ConsoleUtils.printError("Erro: Formato de data inválido. Use YYYY-MM-DD.");
            } catch (Exception e) {
                ConsoleUtils.printError("Erro na operação de rotas: " + e.getMessage());
            }
        } while (subOpcao != 0);
    }

    private void cadastrarRota() throws Exception {
        ConsoleUtils.println("\n--- Cadastro de Rota ---");
        ConsoleUtils.print("Data da Rota (YYYY-MM-DD)");
        String dataStr = scanner.nextLine();
        LocalDate dataRota = LocalDate.parse(dataStr);

        ConsoleUtils.print("Observações (opcional)");
        String observacoes = scanner.nextLine();

        ConsoleUtils.print("ID do Motorista");
        Long motoristaId = scanner.nextLong();
        scanner.nextLine();

        ConsoleUtils.print("ID do Veículo");
        Long veiculoId = scanner.nextLong();
        scanner.nextLine();

        Optional<Motorista> motoristaOpt = motoristaController.getById(motoristaId);
        Optional<Veiculo> veiculoOpt = veiculoController.getById(veiculoId);

        if (motoristaOpt.isPresent() && veiculoOpt.isPresent()) {
            Rota novaRota = new Rota(dataRota, observacoes, motoristaOpt.get(), veiculoOpt.get());
            rotaController.save(novaRota);
            ConsoleUtils.printSuccess("Rota cadastrada com sucesso!");
        } else {
            ConsoleUtils.println("Motorista ou Veículo não encontrado. Operação cancelada.");
        }
    }

    private void listarTodasRotas() {
        ConsoleUtils.println("\n--- Todas as Rotas ---");
        var rotas = rotaController.getAll();
        if (rotas.isEmpty()) {
            ConsoleUtils.printInfo("Nenhuma rota cadastrada no sistema.");
        } else {
            rotas.forEach(rota -> ConsoleUtils.println(rota.toString()));
        }
    }

    private void buscarRotaPorId() {
        ConsoleUtils.println("\n--- Buscar Rota por ID ---");
        ConsoleUtils.print("Digite o ID da rota");
        Long idBusca = scanner.nextLong();
        scanner.nextLine();
        Optional<Rota> rotaOpt = rotaController.getById(idBusca);
        if (rotaOpt.isPresent()) {
            ConsoleUtils.printDivider();
            ConsoleUtils.println(rotaOpt.get().toString());
            ConsoleUtils.printDivider();
            ConsoleUtils.printError(ROTA_COM_ID + idBusca + " não encontrada.");
        }
    }

    private void atualizarRota() throws Exception {
        ConsoleUtils.println("\n--- Atualizar Rota ---");
        ConsoleUtils.print("Digite o ID da rota a ser atualizada");
        Long idUpdate = scanner.nextLong();
        scanner.nextLine();
        Optional<Rota> rotaExistenteOpt = rotaController.getById(idUpdate);
        if (rotaExistenteOpt.isPresent()) {
            Rota rotaExistente = rotaExistenteOpt.get();
            ConsoleUtils.println("Rota encontrada. Digite os novos dados (deixe em branco para manter o atual):");
            atualizarDataRota(rotaExistente);
            atualizarObservacoesRota(rotaExistente);
            atualizarMotoristaRota(rotaExistente);
            atualizarVeiculoRota(rotaExistente);
            rotaController.update(idUpdate, rotaExistente);
            ConsoleUtils.printSuccess("Rota atualizada com sucesso!");
            ConsoleUtils.printError(ROTA_COM_ID + idUpdate + " não encontrada. Não foi possível atualizar.");
        }
    }

    private void atualizarDataRota(Rota rota) {
        ConsoleUtils.print("Nova Data da Rota (" + rota.getDataRota() + ") (YYYY-MM-DD)");
        String novaDataStr = scanner.nextLine();
        if (!novaDataStr.trim().isEmpty()) {
            rota.setDataRota(LocalDate.parse(novaDataStr));
        }
    }

    private void atualizarObservacoesRota(Rota rota) {
        ConsoleUtils.print("Novas Observações (" + rota.getObservacoes() + ")");
        String novasObservacoes = scanner.nextLine();
        if (!novasObservacoes.trim().isEmpty()) {
            rota.setObservacoes(novasObservacoes);
        }
    }

    private void atualizarMotoristaRota(Rota rota) {
        ConsoleUtils.print("Novo ID do Motorista (" + (rota.getMotorista() != null ? rota.getMotorista().getId() : "null") + ")");
        String novoMotoristaIdStr = scanner.nextLine();
        if (!novoMotoristaIdStr.trim().isEmpty()) {
            Long novoMotoristaId = Long.parseLong(novoMotoristaIdStr);
            Optional<Motorista> novoMotoristaOpt = motoristaController.getById(novoMotoristaId);
            if (novoMotoristaOpt.isPresent()) {
                rota.setMotorista(novoMotoristaOpt.get());
            } else {
                ConsoleUtils.println("Motorista com ID " + novoMotoristaId + " não encontrado. Motorista não será atualizado.");
            }
        }
    }

    private void atualizarVeiculoRota(Rota rota) {
        ConsoleUtils.print("Novo ID do Veículo (" + (rota.getVeiculo() != null ? rota.getVeiculo().getId() : "null") + ")");
        String novoVeiculoIdStr = scanner.nextLine();
        if (!novoVeiculoIdStr.trim().isEmpty()) {
            Long novoVeiculoId = Long.parseLong(novoVeiculoIdStr);
            Optional<Veiculo> novoVeiculoOpt = veiculoController.getById(novoVeiculoId);
            if (novoVeiculoOpt.isPresent()) {
                rota.setVeiculo(novoVeiculoOpt.get());
            } else {
                ConsoleUtils.println("Veículo com ID " + novoVeiculoId + " não encontrado. Veículo não será atualizado.");
            }
        }
    }

    private void excluirRota() {
        ConsoleUtils.println("\n--- Excluir Rota ---");
        ConsoleUtils.print("Digite o ID da rota a ser excluída");
        Long idDelete = scanner.nextLong();
        scanner.nextLine();
        Optional<Rota> rotaOpt = rotaController.getById(idDelete);
        if (rotaOpt.isPresent()) {
            if (rotaController.delete(idDelete)) {
                ConsoleUtils.printSuccess("Rota excluída com sucesso!");
            } else {
                ConsoleUtils.printError("Não foi possível excluir a rota. Tente novamente ou verifique se a rota está vinculada a outros registros.");
            }
            ConsoleUtils.printError(ROTA_COM_ID + idDelete + " não encontrada. Não foi possível excluir.");
        }
    }

    private void listarColetasPendentesDaRota() {
        ConsoleUtils.println("\n--- Listar Coletas Pendentes de uma Rota ---");
        ConsoleUtils.print("Digite o ID da rota");
        Long idRota = scanner.nextLong();
        scanner.nextLine();
        Optional<Rota> rotaOpt = rotaController.getById(idRota);
        if (rotaOpt.isPresent()) {
            var coletasPendentes = rotaController.getColetasPendentes(rotaOpt.get());
            if (coletasPendentes.isEmpty()) {
                ConsoleUtils.println("Nenhuma coleta pendente nesta rota.");
            } else {
                ConsoleUtils.println("Coletas pendentes:");
                coletasPendentes.forEach(c -> ConsoleUtils.println(c.toString()));
            }
        } else {
            ConsoleUtils.println("Rota não encontrada.");
        }
    }
}