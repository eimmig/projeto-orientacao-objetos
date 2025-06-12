package com.br.ecoleta.view;

import com.br.ecoleta.controller.MotoristaController;
import com.br.ecoleta.controller.VeiculoController;
import com.br.ecoleta.model.Motorista;
import com.br.ecoleta.model.Rota;
import com.br.ecoleta.model.Coleta;
import com.br.ecoleta.service.RotaService;
import com.br.ecoleta.util.ConsoleUtils;
import com.br.ecoleta.exception.ValidationException;
import java.util.Optional;
import java.util.Scanner;

public class MotoristaView {
    private static final String MOTORISTA_ID_MSG = "Motorista com ID ";
    private final MotoristaController motoristaController;
    private final VeiculoController veiculoController;
    private final RotaService rotaService;
    private final Scanner scanner;

    public MotoristaView(MotoristaController motoristaController, VeiculoController veiculoController, RotaService rotaService, Scanner scanner) {
        this.motoristaController = motoristaController;
        this.veiculoController = veiculoController;
        this.rotaService = rotaService;
        this.scanner = scanner;
    }

    public void exibirMenuMotorista() {
        int subOpcao;
        do {
            ConsoleUtils.println("\n--- Gerenciar Motoristas ---");
            ConsoleUtils.println("1. Cadastrar Motorista");
            ConsoleUtils.println("2. Listar Todos os Motoristas");
            ConsoleUtils.println("3. Buscar Motorista por ID");
            ConsoleUtils.println("4. Atualizar Motorista");
            ConsoleUtils.println("5. Excluir Motorista");
            ConsoleUtils.println("6. Buscar Rota do Dia");
            ConsoleUtils.println("0. Voltar ao Menu Principal");

            ConsoleUtils.print("Opção");
            subOpcao = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (subOpcao) {
                    case 1:
                        cadastrarMotorista();
                        break;
                    case 2:
                        listarTodosMotoristas();
                        break;
                    case 3:
                        buscarMotoristaPorId();
                        break;
                    case 4:
                        atualizarMotorista();
                        break;
                    case 5:
                        excluirMotorista();
                        break;
                    case 6:
                        buscarRotaDoDia();
                        break;
                    case 0:
                        ConsoleUtils.println("Voltando ao Menu Principal.");
                        break;
                    default:
                        ConsoleUtils.println("Opção inválida. Tente novamente.");
                }
            } catch (Exception e) {
                ConsoleUtils.printError("Erro na operação de motoristas: " + e.getMessage());
            }
        } while (subOpcao != 0);
    }

    private void cadastrarMotorista() throws Exception {
        ConsoleUtils.println("\n--- Cadastro de Motorista ---");
        ConsoleUtils.print("Nome");
        String nome = scanner.nextLine();
        if (nome.trim().isEmpty()) {
            ConsoleUtils.printError("O nome do motorista não pode estar vazio. Por favor, preencha corretamente.");
            throw new ValidationException("Nome não pode estar vazio");
        }
        ConsoleUtils.print("CPF");
        String cpf = scanner.nextLine();
        if (cpf.trim().isEmpty()) {
            ConsoleUtils.printError("O CPF do motorista não pode estar vazio. Por favor, preencha corretamente.");
            throw new ValidationException("CPF não pode estar vazio");
        }
        ConsoleUtils.print("CNH");
        String cnh = scanner.nextLine();
        if (cnh.trim().isEmpty()) {
            ConsoleUtils.printError("A CNH do motorista não pode estar vazia. Por favor, preencha corretamente.");
            throw new ValidationException("CNH não pode estar vazia");
        }
        ConsoleUtils.print("Telefone");
        String telefone = scanner.nextLine();
        if (telefone.trim().isEmpty()) {
            ConsoleUtils.printError("O telefone do motorista não pode estar vazio. Por favor, preencha corretamente.");
            throw new ValidationException("Telefone não pode estar vazio");
        }
        Motorista novoMotorista = new Motorista(nome, cpf, cnh, telefone);
        motoristaController.save(novoMotorista);
        ConsoleUtils.printSuccess("Motorista cadastrado com sucesso!");
    }

    private void listarTodosMotoristas() {
        ConsoleUtils.println("\n--- Todos os Motoristas ---");
        var motoristas = motoristaController.getAll();
        if (motoristas.isEmpty()) {
            ConsoleUtils.printInfo("Nenhum motorista cadastrado no sistema.");
        } else {
            motoristas.forEach(motorista -> ConsoleUtils.println(motorista.toString()));
        }
    }

    private void buscarMotoristaPorId() {
        ConsoleUtils.println("\n--- Buscar Motorista por ID ---");
        ConsoleUtils.print("Digite o ID do motorista");
        Long idBusca = scanner.nextLong();
        scanner.nextLine();
        Optional<Motorista> motoristaOpt = motoristaController.getById(idBusca);
        if (motoristaOpt.isPresent()) {
            ConsoleUtils.printDivider();
            ConsoleUtils.println(motoristaOpt.get().toString());
            ConsoleUtils.printDivider();
            ConsoleUtils.printError(MOTORISTA_ID_MSG + idBusca + " não encontrado.");
        }
    }

    private void atualizarMotorista() throws Exception {
        ConsoleUtils.println("\n--- Atualizar Motorista ---");
        ConsoleUtils.print("Digite o ID do motorista a ser atualizado");
        Long idUpdate = scanner.nextLong();
        scanner.nextLine();
        Optional<Motorista> motoristaExistenteOpt = motoristaController.getById(idUpdate);
        if (motoristaExistenteOpt.isPresent()) {
            Motorista motoristaExistente = motoristaExistenteOpt.get();
            ConsoleUtils.println("Motorista encontrado. Digite os novos dados (deixe em branco para manter o atual):");
            ConsoleUtils.print("Novo Nome (" + motoristaExistente.getNome() + ")");
            String novoNome = scanner.nextLine();
            if (!novoNome.trim().isEmpty()) motoristaExistente.setNome(novoNome);
            ConsoleUtils.print("Novo CPF (" + motoristaExistente.getCpf() + ")");
            String novoCpf = scanner.nextLine();
            if (!novoCpf.trim().isEmpty()) motoristaExistente.setCpf(novoCpf);
            ConsoleUtils.print("Nova CNH (" + motoristaExistente.getCnh() + ")");
            String novaCnh = scanner.nextLine();
            if (!novaCnh.trim().isEmpty()) motoristaExistente.setCnh(novaCnh);
            ConsoleUtils.print("Novo Telefone (" + motoristaExistente.getTelefone() + ")");
            String novoTelefone = scanner.nextLine();
            if (!novoTelefone.trim().isEmpty()) motoristaExistente.setTelefone(novoTelefone);
            motoristaController.update(idUpdate, motoristaExistente);
            ConsoleUtils.printSuccess("Motorista atualizado com sucesso!");
            ConsoleUtils.printError(MOTORISTA_ID_MSG + idUpdate + " não encontrado. Não foi possível atualizar.");
        }
    }

    private void excluirMotorista() {
        ConsoleUtils.println("\n--- Excluir Motorista ---");
        ConsoleUtils.print("Digite o ID do motorista a ser excluído");
        Long idDelete = scanner.nextLong();
        scanner.nextLine();
        Optional<Motorista> motoristaOpt = motoristaController.getById(idDelete);
        if (motoristaOpt.isPresent()) {
            if (motoristaController.delete(idDelete)) {
                ConsoleUtils.printSuccess("Motorista excluído com sucesso!");
            } else {
                ConsoleUtils.printError("Não foi possível excluir o motorista. Tente novamente ou verifique se o motorista está vinculado a outros registros.");
            }
            ConsoleUtils.printError(MOTORISTA_ID_MSG + idDelete + " não encontrado. Não foi possível excluir.");
        }
    }

    private void buscarRotaDoDia() {
        ConsoleUtils.println("\n--- Buscar Rota do Dia ---");
        ConsoleUtils.print("Digite o ID do motorista");
        Long motoristaId = scanner.nextLong();
        scanner.nextLine();
        ConsoleUtils.print("Digite o ID do veículo");
        Long veiculoId = scanner.nextLong();
        scanner.nextLine();
        var motoristaOpt = motoristaController.getById(motoristaId);
        var veiculoOpt = veiculoController.getById(veiculoId);
        if (motoristaOpt.isEmpty() || veiculoOpt.isEmpty()) {
            ConsoleUtils.println("Motorista ou veículo não encontrado.");
            return;
        }
        Rota rota = rotaService.buscarOuGerarRotaDoDia(motoristaOpt.get(), veiculoOpt.get());
        if (rota == null) {
            ConsoleUtils.println("Nenhuma rota disponível para hoje para este motorista e veículo.");
            return;
        }
        ConsoleUtils.println("Rota do dia:");
        ConsoleUtils.println(rota != null ? rota.toString() : "Rota não encontrada.");
        if (rota.getColetas() == null || rota.getColetas().isEmpty()) {
            ConsoleUtils.println("Nenhuma coleta atribuída nesta rota.");
        } else {
            ConsoleUtils.println("Coletas nesta rota:");
            for (Coleta coleta : rota.getColetas()) {
                ConsoleUtils.println(coleta != null ? coleta.toString() : "Coleta não encontrada.");
            }
        }
    }
}