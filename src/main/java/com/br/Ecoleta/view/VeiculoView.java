package com.br.ecoleta.view;

import com.br.ecoleta.controller.VeiculoController;
import com.br.ecoleta.model.Veiculo;
import com.br.ecoleta.util.TipoVeiculo;
import java.util.Optional;
import java.util.Scanner;

public class VeiculoView {
    private final VeiculoController veiculoController;
    private final Scanner scanner;

    public VeiculoView(VeiculoController veiculoController, Scanner scanner) {
        this.veiculoController = veiculoController;
        this.scanner = scanner;
    }

    public void exibirMenuVeiculo() {
        int subOpcao;
        do {
            System.out.println("\n--- Gerenciar Veículos ---");
            System.out.println("1. Cadastrar Veículo");
            System.out.println("2. Listar Todos os Veículos");
            System.out.println("3. Buscar Veículo por ID");
            System.out.println("4. Atualizar Veículo");
            System.out.println("5. Excluir Veículo");
            System.out.println("6. Listar Veículos sem Rota/Motorista");
            System.out.println("0. Voltar ao Menu Principal");

            System.out.print("Opção: ");
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
                        System.out.println("Voltando ao Menu Principal.");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Erro: Modelo de veículo inválido. Por favor, use um dos valores listados.");
            } catch (Exception e) {
                System.err.println("Erro na operação de veículos: " + e.getMessage());
            }
        } while (subOpcao != 0);
    }

    private void cadastrarVeiculo() throws Exception {
        System.out.println("\n--- Cadastro de Veículo ---");
        System.out.print("Placa: ");
        String placa = scanner.nextLine();
        System.out.print("Modelo (CAMINHAO, CARRO, CAMINHONETE, SUV, VAN): ");
        String modeloStr = scanner.nextLine().toUpperCase();
        TipoVeiculo modelo = TipoVeiculo.valueOf(modeloStr);
        System.out.print("Capacidade (KG): ");
        Double capacidadeKg = scanner.nextDouble();
        scanner.nextLine();

        Veiculo novoVeiculo = new Veiculo(placa, modelo, capacidadeKg);
        veiculoController.save(novoVeiculo);
    }

    private void listarTodosVeiculos() {
        System.out.println("\n--- Todos os Veículos ---");
        veiculoController.getAll();
    }

    private void buscarVeiculoPorId() {
        System.out.println("\n--- Buscar Veículo por ID ---");
        System.out.print("Digite o ID do veículo: ");
        Long idBusca = scanner.nextLong();
        scanner.nextLine();
        veiculoController.getById(idBusca);
    }

    private void atualizarVeiculo() throws Exception {
        System.out.println("\n--- Atualizar Veículo ---");
        System.out.print("Digite o ID do veículo a ser atualizado: ");
        Long idUpdate = scanner.nextLong();
        scanner.nextLine();

        Optional<Veiculo> veiculoExistenteOpt = veiculoController.getById(idUpdate);
        if (veiculoExistenteOpt.isPresent()) {
            Veiculo veiculoExistente = veiculoExistenteOpt.get();
            System.out.println("Veículo encontrado. Digite os novos dados (deixe em branco para manter o atual):");

            System.out.print("Nova Placa (" + veiculoExistente.getPlaca() + "): ");
            String novaPlaca = scanner.nextLine();
            if (!novaPlaca.trim().isEmpty()) veiculoExistente.setPlaca(novaPlaca);

            System.out.print("Novo Modelo (" + veiculoExistente.getModelo() + ") (CAMINHAO, CARRO, CAMINHONETE, SUV, VAN): ");
            String novoModeloStr = scanner.nextLine().toUpperCase();
            if (!novoModeloStr.trim().isEmpty()) {
                veiculoExistente.setModelo(TipoVeiculo.valueOf(novoModeloStr));
            }

            System.out.print("Nova Capacidade (KG) (" + veiculoExistente.getCapacidadeKg() + "): ");
            String capacidadeStr = scanner.nextLine();
            if (!capacidadeStr.trim().isEmpty()) veiculoExistente.setCapacidadeKg(Double.parseDouble(capacidadeStr));

            veiculoController.update(idUpdate, veiculoExistente);
        } else {
            System.out.println("Veículo com ID " + idUpdate + " não encontrado.");
        }
    }

    private void excluirVeiculo() {
        System.out.println("\n--- Excluir Veículo ---");
        System.out.print("Digite o ID do veículo a ser excluído: ");
        Long idDelete = scanner.nextLong();
        scanner.nextLine();
        veiculoController.delete(idDelete);
    }

    private void listarVeiculosSemRotaOuMotorista() {
        System.out.println("\n--- Veículos sem Rota/Motorista ---");
        var veiculos = veiculoController.getVeiculosSemRotaOuMotorista();
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo desvinculado encontrado.");
        } else {
            veiculos.forEach(System.out::println);
        }
    }
}