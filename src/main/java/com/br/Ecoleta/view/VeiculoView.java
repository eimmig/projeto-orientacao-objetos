package com.br.ecoleta.view;

import com.br.ecoleta.controller.VeiculoController;
import com.br.ecoleta.exception.EntityNotFoundException;
import com.br.ecoleta.exception.VeiculoException;
import com.br.ecoleta.model.Veiculo;
import com.br.ecoleta.util.ConsoleUtils;
import com.br.ecoleta.util.TipoVeiculo;
import java.util.Optional;
import java.util.Scanner;

public class VeiculoView {
    private static final String VEICULO_COM_ID = "Veículo com ID ";
    private final VeiculoController veiculoController;
    private final Scanner scanner;

    public VeiculoView(VeiculoController veiculoController, Scanner scanner) {
        this.veiculoController = veiculoController;
        this.scanner = scanner;
    }

    public void exibirMenuVeiculo() {
        int subOpcao;
        do {
            ConsoleUtils.println("\n--- Gerenciar Veículos ---");
            ConsoleUtils.println("1. Cadastrar Veículo");
            ConsoleUtils.println("2. Listar Todos os Veículos");
            ConsoleUtils.println("3. Buscar Veículo por ID");
            ConsoleUtils.println("4. Atualizar Veículo");
            ConsoleUtils.println("5. Excluir Veículo");
            ConsoleUtils.println("6. Listar Veículos sem Rota/Motorista");
            ConsoleUtils.println("0. Voltar ao Menu Principal");

            ConsoleUtils.print("Opção");
            subOpcao = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (subOpcao) {
                    case 1:
                        cadastrarVeiculo();
                        break;
                    case 2:
                        listarTodosVeiculos();
                        break;
                    case 3:
                        buscarVeiculoPorId();
                        break;
                    case 4:
                        atualizarVeiculo();
                        break;
                    case 5:
                        excluirVeiculo();
                        break;
                    case 6:
                        listarVeiculosSemRotaOuMotorista();
                        break;
                    case 0:
                        ConsoleUtils.println("Voltando ao Menu Principal.");
                        break;
                    default:
                        ConsoleUtils.println("Opção inválida. Tente novamente.");
                }
            } catch (IllegalArgumentException e) {
                ConsoleUtils.printError("Erro: Modelo de veículo inválido. Por favor, use um dos valores listados.");
            } catch (VeiculoException e) {
                ConsoleUtils.printError("Erro de veículo: " + e.getMessage());
            } catch (Exception e) {
                ConsoleUtils.printError("Erro inesperado: " + e.getMessage());
            }
        } while (subOpcao != 0);
    }

    private void cadastrarVeiculo() {
        ConsoleUtils.println("\n--- Cadastro de Veículo ---");
        ConsoleUtils.print("Placa");
        String placa = scanner.nextLine();
        if (placa.trim().isEmpty()) {
            ConsoleUtils.printError("A placa do veículo não pode estar vazia. Por favor, preencha corretamente.");
            throw new VeiculoException("Placa não pode estar vazia");
        }
        ConsoleUtils.print("Modelo (CAMINHAO, CARRO, CAMINHONETE, SUV, VAN)");
        String modeloStr = scanner.nextLine().toUpperCase();
        TipoVeiculo modelo = TipoVeiculo.valueOf(modeloStr);
        ConsoleUtils.print("Capacidade (KG)");
        Double capacidadeKg = scanner.nextDouble();
        scanner.nextLine();
        Veiculo novoVeiculo = new Veiculo(placa, modelo, capacidadeKg);
        veiculoController.save(novoVeiculo);
        ConsoleUtils.printSuccess("Veículo cadastrado com sucesso!");
    }

    private void listarTodosVeiculos() {
        ConsoleUtils.println("\n--- Todos os Veículos ---");
        var veiculos = veiculoController.getAll();
        if (veiculos.isEmpty()) {
            ConsoleUtils.printInfo("Nenhum veículo cadastrado no sistema.");
        } else {
            veiculos.forEach(veiculo -> ConsoleUtils.println(veiculo.toString()));
        }
    }

    private void buscarVeiculoPorId() {
        ConsoleUtils.println("\n--- Buscar Veículo por ID ---");
        ConsoleUtils.print("Digite o ID do veículo");
        Long idBusca = scanner.nextLong();
        scanner.nextLine();
        Optional<Veiculo> veiculoOpt = veiculoController.getById(idBusca);
        if (veiculoOpt.isPresent()) {
            ConsoleUtils.printDivider();
            ConsoleUtils.println(veiculoOpt.get().toString());
            ConsoleUtils.printDivider();
            ConsoleUtils.printError(VEICULO_COM_ID + idBusca + " não encontrado.");
        }
    }

    private void atualizarVeiculo() throws EntityNotFoundException {
        ConsoleUtils.println("\n--- Atualizar Veículo ---");
        ConsoleUtils.print("Digite o ID do veículo a ser atualizado");
        Long idUpdate = scanner.nextLong();
        scanner.nextLine();
        Optional<Veiculo> veiculoExistenteOpt = veiculoController.getById(idUpdate);
        if (veiculoExistenteOpt.isPresent()) {
            Veiculo veiculoExistente = veiculoExistenteOpt.get();
            ConsoleUtils.println("Veículo encontrado. Digite os novos dados (deixe em branco para manter o atual):");
            ConsoleUtils.print("Nova Placa (" + veiculoExistente.getPlaca() + ")");
            String novaPlaca = scanner.nextLine();
            if (!novaPlaca.trim().isEmpty()) veiculoExistente.setPlaca(novaPlaca);
            ConsoleUtils.print("Novo Modelo (" + veiculoExistente.getModelo() + ") (CAMINHAO, CARRO, CAMINHONETE, SUV, VAN)");
            String novoModeloStr = scanner.nextLine().toUpperCase();
            if (!novoModeloStr.trim().isEmpty()) {
                veiculoExistente.setModelo(TipoVeiculo.valueOf(novoModeloStr));
            }
            ConsoleUtils.print("Nova Capacidade (KG) (" + veiculoExistente.getCapacidadeKg() + ")");
            String capacidadeStr = scanner.nextLine();
            if (!capacidadeStr.trim().isEmpty()) veiculoExistente.setCapacidadeKg(Double.parseDouble(capacidadeStr));
            veiculoController.update(idUpdate, veiculoExistente);
            ConsoleUtils.printSuccess("Veículo atualizado com sucesso!");
            ConsoleUtils.printError(VEICULO_COM_ID + idUpdate + " não encontrado. Não foi possível atualizar.");
        }
    }

    private void excluirVeiculo() {
        ConsoleUtils.println("\n--- Excluir Veículo ---");
        ConsoleUtils.print("Digite o ID do veículo a ser excluído");
        Long idDelete = scanner.nextLong();
        scanner.nextLine();
        Optional<Veiculo> veiculoOpt = veiculoController.getById(idDelete);
        if (veiculoOpt.isPresent()) {
            if (veiculoController.delete(idDelete)) {
                ConsoleUtils.printSuccess("Veículo excluído com sucesso!");
            } else {
                ConsoleUtils.printError("Não foi possível excluir o veículo. Tente novamente ou verifique se o veículo está vinculado a outros registros.");
            }
            ConsoleUtils.printError(VEICULO_COM_ID + idDelete + " não encontrado. Não foi possível excluir.");
        }
    }

    private void listarVeiculosSemRotaOuMotorista() {
        ConsoleUtils.println("\n--- Veículos sem Rota/Motorista ---");
        var veiculos = veiculoController.getVeiculosSemRotaOuMotorista();
        if (veiculos.isEmpty()) {
            ConsoleUtils.println("Nenhum veículo desvinculado encontrado.");
        } else {
            veiculos.forEach(v -> ConsoleUtils.println(v.toString()));
        }
    }
}