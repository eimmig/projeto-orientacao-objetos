package com.br.Ecoleta.view;

import com.br.Ecoleta.controller.ClienteController;
import com.br.Ecoleta.model.Cliente;
import java.util.Optional;
import java.util.Scanner;

public class ClienteView {
    private final ClienteController clienteController;
    private final Scanner scanner;

    public ClienteView(ClienteController clienteController, Scanner scanner) {
        this.clienteController = clienteController;
        this.scanner = scanner;
    }

    public void exibirMenuCliente() {
        int subOpcao;
        do {
            System.out.println("\n--- Gerenciar Clientes ---");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Todos os Clientes");
            System.out.println("3. Buscar Cliente por ID");
            System.out.println("4. Atualizar Cliente");
            System.out.println("5. Excluir Cliente");
            System.out.println("0. Voltar ao Menu Principal");

            System.out.print("Opção: ");
            subOpcao = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (subOpcao) {
                    case 1:
                        cadastrarCliente();
                        break;
                    case 2:
                        listarTodosClientes();
                        break;
                    case 3:
                        buscarClientePorId();
                        break;
                    case 4:
                        atualizarCliente();
                        break;
                    case 5:
                        excluirCliente();
                        break;
                    case 0:
                        System.out.println("Voltando ao Menu Principal.");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (Exception e) {
                System.err.println("Erro na operação de clientes: " + e.getMessage());
            }
        } while (subOpcao != 0);
    }

    private void cadastrarCliente() throws Exception {
        System.out.println("\n--- Cadastro de Cliente ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Documento (CPF/CNPJ): ");
        String documento = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        Cliente novoCliente = new Cliente(nome, documento, email, telefone);
        clienteController.save(novoCliente);
    }

    private void listarTodosClientes() {
        System.out.println("\n--- Todos os Clientes ---");
        clienteController.getAll();
    }

    private void buscarClientePorId() {
        System.out.println("\n--- Buscar Cliente por ID ---");
        System.out.print("Digite o ID do cliente: ");
        Long idBusca = scanner.nextLong();
        scanner.nextLine();
        clienteController.getById(idBusca);
    }

    private void atualizarCliente() throws Exception {
        System.out.println("\n--- Atualizar Cliente ---");
        System.out.print("Digite o ID do cliente a ser atualizado: ");
        Long idUpdate = scanner.nextLong();
        scanner.nextLine();

        Optional<Cliente> clienteExistenteOpt = clienteController.getById(idUpdate);
        if (clienteExistenteOpt.isPresent()) {
            Cliente clienteExistente = clienteExistenteOpt.get();
            System.out.println("Cliente encontrado. Digite os novos dados (deixe em branco para manter o atual):");
            System.out.print("Novo Nome (" + clienteExistente.getNome() + "): ");
            String novoNome = scanner.nextLine();
            if (!novoNome.trim().isEmpty()) clienteExistente.setNome(novoNome);

            System.out.print("Novo Documento (" + clienteExistente.getDocumento() + "): ");
            String novoDocumento = scanner.nextLine();
            if (!novoDocumento.trim().isEmpty()) clienteExistente.setDocumento(novoDocumento);

            System.out.print("Novo Email (" + clienteExistente.getEmail() + "): ");
            String novoEmail = scanner.nextLine();
            if (!novoEmail.trim().isEmpty()) clienteExistente.setEmail(novoEmail);

            System.out.print("Novo Telefone (" + clienteExistente.getTelefone() + "): ");
            String novoTelefone = scanner.nextLine();
            if (!novoTelefone.trim().isEmpty()) clienteExistente.setTelefone(novoTelefone);

            clienteController.update(idUpdate, clienteExistente);
        } else {
            System.out.println("Cliente com ID " + idUpdate + " não encontrado.");
        }
    }

    private void excluirCliente() {
        System.out.println("\n--- Excluir Cliente ---");
        System.out.print("Digite o ID do cliente a ser excluído: ");
        Long idDelete = scanner.nextLong();
        scanner.nextLine();
        clienteController.delete(idDelete);
    }
}