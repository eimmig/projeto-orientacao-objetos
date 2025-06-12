package com.br.ecoleta.view;

import com.br.ecoleta.controller.MotoristaController;
import com.br.ecoleta.controller.RotaController;
import com.br.ecoleta.controller.VeiculoController;
import com.br.ecoleta.model.Motorista;
import com.br.ecoleta.model.Rota;
import com.br.ecoleta.model.Veiculo;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

public class RotaView {
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
            System.out.println("\n--- Gerenciar Rotas ---");
            System.out.println("1. Cadastrar Rota");
            System.out.println("2. Listar Todas as Rotas");
            System.out.println("3. Buscar Rota por ID");
            System.out.println("4. Atualizar Rota");
            System.out.println("5. Excluir Rota");
            System.out.println("6. Listar Coletas Pendentes de uma Rota");
            System.out.println("0. Voltar ao Menu Principal");

            System.out.print("Opção: ");
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
                        System.out.println("Voltando ao Menu Principal.");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (DateTimeParseException e) {
                System.err.println("Erro: Formato de data inválido. Use YYYY-MM-DD.");
            } catch (Exception e) {
                System.err.println("Erro na operação de rotas: " + e.getMessage());
            }
        } while (subOpcao != 0);
    }

    private void cadastrarRota() throws Exception {
        System.out.println("\n--- Cadastro de Rota ---");
        System.out.print("Data da Rota (YYYY-MM-DD): ");
        String dataStr = scanner.nextLine();
        LocalDate dataRota = LocalDate.parse(dataStr);

        System.out.print("Observações (opcional): ");
        String observacoes = scanner.nextLine();

        System.out.print("ID do Motorista: ");
        Long motoristaId = scanner.nextLong();
        scanner.nextLine();

        System.out.print("ID do Veículo: ");
        Long veiculoId = scanner.nextLong();
        scanner.nextLine();

        Optional<Motorista> motoristaOpt = motoristaController.getById(motoristaId);
        Optional<Veiculo> veiculoOpt = veiculoController.getById(veiculoId);

        if (motoristaOpt.isPresent() && veiculoOpt.isPresent()) {
            Rota novaRota = new Rota(dataRota, observacoes, motoristaOpt.get(), veiculoOpt.get());
            rotaController.save(novaRota);
        } else {
            System.out.println("Motorista ou Veículo não encontrado. Operação cancelada.");
        }
    }

    private void listarTodasRotas() {
        System.out.println("\n--- Todas as Rotas ---");
        rotaController.getAll();
    }

    private void buscarRotaPorId() {
        System.out.println("\n--- Buscar Rota por ID ---");
        System.out.print("Digite o ID da rota: ");
        Long idBusca = scanner.nextLong();
        scanner.nextLine();
        rotaController.getById(idBusca);
    }

    private void atualizarRota() throws Exception {
        System.out.println("\n--- Atualizar Rota ---");
        System.out.print("Digite o ID da rota a ser atualizada: ");
        Long idUpdate = scanner.nextLong();
        scanner.nextLine();

        Optional<Rota> rotaExistenteOpt = rotaController.getById(idUpdate);
        if (rotaExistenteOpt.isPresent()) {
            Rota rotaExistente = rotaExistenteOpt.get();
            System.out.println("Rota encontrada. Digite os novos dados (deixe em branco para manter o atual):");

            System.out.print("Nova Data da Rota (" + rotaExistente.getDataRota() + ") (YYYY-MM-DD): ");
            String novaDataStr = scanner.nextLine();
            if (!novaDataStr.trim().isEmpty()) rotaExistente.setDataRota(LocalDate.parse(novaDataStr));

            System.out.print("Novas Observações (" + rotaExistente.getObservacoes() + "): ");
            String novasObservacoes = scanner.nextLine();
            if (!novasObservacoes.trim().isEmpty()) rotaExistente.setObservacoes(novasObservacoes);

            System.out.print("Novo ID do Motorista (" + (rotaExistente.getMotorista() != null ? rotaExistente.getMotorista().getId() : "null") + "): ");
            String novoMotoristaIdStr = scanner.nextLine();
            if (!novoMotoristaIdStr.trim().isEmpty()) {
                Long novoMotoristaId = Long.parseLong(novoMotoristaIdStr);
                Optional<Motorista> novoMotoristaOpt = motoristaController.getById(novoMotoristaId);
                if (novoMotoristaOpt.isPresent()) {
                    rotaExistente.setMotorista(novoMotoristaOpt.get());
                } else {
                    System.out.println("Motorista com ID " + novoMotoristaId + " não encontrado. Motorista não será atualizado.");
                }
            }

            System.out.print("Novo ID do Veículo (" + (rotaExistente.getVeiculo() != null ? rotaExistente.getVeiculo().getId() : "null") + "): ");
            String novoVeiculoIdStr = scanner.nextLine();
            if (!novoVeiculoIdStr.trim().isEmpty()) {
                Long novoVeiculoId = Long.parseLong(novoVeiculoIdStr);
                Optional<Veiculo> novoVeiculoOpt = veiculoController.getById(novoVeiculoId);
                if (novoVeiculoOpt.isPresent()) {
                    rotaExistente.setVeiculo(novoVeiculoOpt.get());
                } else {
                    System.out.println("Veículo com ID " + novoVeiculoId + " não encontrado. Veículo não será atualizado.");
                }
            }

            rotaController.update(idUpdate, rotaExistente);
        } else {
            System.out.println("Rota com ID " + idUpdate + " não encontrada.");
        }
    }

    private void excluirRota() {
        System.out.println("\n--- Excluir Rota ---");
        System.out.print("Digite o ID da rota a ser excluída: ");
        Long idDelete = scanner.nextLong();
        scanner.nextLine();
        rotaController.delete(idDelete);
    }

    private void listarColetasPendentesDaRota() {
        System.out.println("\n--- Listar Coletas Pendentes de uma Rota ---");
        System.out.print("Digite o ID da rota: ");
        Long idRota = scanner.nextLong();
        scanner.nextLine();
        Optional<Rota> rotaOpt = rotaController.getById(idRota);
        if (rotaOpt.isPresent()) {
            var coletasPendentes = rotaController.getColetasPendentes(rotaOpt.get());
            if (coletasPendentes.isEmpty()) {
                System.out.println("Nenhuma coleta pendente nesta rota.");
            } else {
                System.out.println("Coletas pendentes:");
                coletasPendentes.forEach(System.out::println);
            }
        } else {
            System.out.println("Rota não encontrada.");
        }
    }
}