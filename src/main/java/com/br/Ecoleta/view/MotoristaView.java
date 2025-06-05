package com.br.Ecoleta.view;

import com.br.Ecoleta.controller.MotoristaController;
import com.br.Ecoleta.model.Motorista;
import java.util.Optional;
import java.util.Scanner;

public class MotoristaView {
    private final MotoristaController motoristaController;
    private final Scanner scanner;

    public MotoristaView(MotoristaController motoristaController, Scanner scanner) {
        this.motoristaController = motoristaController;
        this.scanner = scanner;
    }

    public void exibirMenuMotorista() {
        int subOpcao;
        do {
            System.out.println("\n--- Gerenciar Motoristas ---");
            System.out.println("1. Cadastrar Motorista");
            System.out.println("2. Listar Todos os Motoristas");
            System.out.println("3. Buscar Motorista por ID");
            System.out.println("4. Atualizar Motorista");
            System.out.println("5. Excluir Motorista");
            System.out.println("0. Voltar ao Menu Principal");

            System.out.print("Opção: ");
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
                    case 0:
                        System.out.println("Voltando ao Menu Principal.");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (Exception e) {
                System.err.println("Erro na operação de motoristas: " + e.getMessage());
            }
        } while (subOpcao != 0);
    }

    private void cadastrarMotorista() throws Exception {
        System.out.println("\n--- Cadastro de Motorista ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("CNH: ");
        String cnh = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        Motorista novoMotorista = new Motorista(nome, cpf, cnh, telefone);
        motoristaController.save(novoMotorista);
    }

    private void listarTodosMotoristas() {
        System.out.println("\n--- Todos os Motoristas ---");
        motoristaController.getAll();
    }

    private void buscarMotoristaPorId() {
        System.out.println("\n--- Buscar Motorista por ID ---");
        System.out.print("Digite o ID do motorista: ");
        Long idBusca = scanner.nextLong();
        scanner.nextLine();
        motoristaController.getById(idBusca);
    }

    private void atualizarMotorista() throws Exception {
        System.out.println("\n--- Atualizar Motorista ---");
        System.out.print("Digite o ID do motorista a ser atualizado: ");
        Long idUpdate = scanner.nextLong();
        scanner.nextLine();

        Optional<Motorista> motoristaExistenteOpt = motoristaController.getById(idUpdate);
        if (motoristaExistenteOpt.isPresent()) {
            Motorista motoristaExistente = motoristaExistenteOpt.get();
            System.out.println("Motorista encontrado. Digite os novos dados (deixe em branco para manter o atual):");

            System.out.print("Novo Nome (" + motoristaExistente.getNome() + "): ");
            String novoNome = scanner.nextLine();
            if (!novoNome.trim().isEmpty()) motoristaExistente.setNome(novoNome);

            System.out.print("Novo CPF (" + motoristaExistente.getCpf() + "): ");
            String novoCpf = scanner.nextLine();
            if (!novoCpf.trim().isEmpty()) motoristaExistente.setCpf(novoCpf);

            System.out.print("Nova CNH (" + motoristaExistente.getCnh() + "): ");
            String novaCnh = scanner.nextLine();
            if (!novaCnh.trim().isEmpty()) motoristaExistente.setCnh(novaCnh);

            System.out.print("Novo Telefone (" + motoristaExistente.getTelefone() + "): ");
            String novoTelefone = scanner.nextLine();
            if (!novoTelefone.trim().isEmpty()) motoristaExistente.setTelefone(novoTelefone);

            motoristaController.update(idUpdate, motoristaExistente);
        } else {
            System.out.println("Motorista com ID " + idUpdate + " não encontrado.");
        }
    }

    private void excluirMotorista() {
        System.out.println("\n--- Excluir Motorista ---");
        System.out.print("Digite o ID do motorista a ser excluído: ");
        Long idDelete = scanner.nextLong();
        scanner.nextLine();
        motoristaController.delete(idDelete);
    }
}