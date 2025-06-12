package com.br.ecoleta.view;

import com.br.ecoleta.controller.ClienteController;
import com.br.ecoleta.model.Cliente;
import com.br.ecoleta.exception.ClienteException;
import com.br.ecoleta.exception.EntityNotFoundException;
import com.br.ecoleta.exception.ValidationException;
import com.br.ecoleta.util.ConsoleUtils;
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
            ConsoleUtils.println("\n--- Gerenciar Clientes ---");
            ConsoleUtils.println("1. Cadastrar Cliente");
            ConsoleUtils.println("2. Listar Todos os Clientes");
            ConsoleUtils.println("3. Buscar Cliente por ID");
            ConsoleUtils.println("4. Atualizar Cliente");
            ConsoleUtils.println("5. Excluir Cliente");
            ConsoleUtils.println("0. Voltar ao Menu Principal");

            ConsoleUtils.print("Opção: ");
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
                        ConsoleUtils.println("Voltando ao Menu Principal.");
                        break;
                    default:
                        ConsoleUtils.printError("Opção inválida. Tente novamente.");
                }
            } catch (ValidationException e) {
                ConsoleUtils.printError("Erro de validação: " + e.getMessage());
            } catch (EntityNotFoundException e) {
                ConsoleUtils.printError(e.getMessage());
            } catch (ClienteException e) {
                ConsoleUtils.printError("Erro no cadastro de cliente: " + e.getMessage());
            } catch (Exception e) {
                ConsoleUtils.printError("Erro inesperado: " + e.getMessage());
            }
        } while (subOpcao != 0);
    }

    private void cadastrarCliente() throws Exception {
        ConsoleUtils.println("\n--- Cadastro de Cliente ---");
        ConsoleUtils.print("Nome");
        String nome = scanner.nextLine();
        
        if (nome.trim().isEmpty()) {
            throw new ValidationException("Nome não pode estar vazio");
        }

        ConsoleUtils.print("Documento (CPF/CNPJ)");
        String documento = scanner.nextLine();
        
        if (documento.trim().isEmpty()) {
            throw new ValidationException("Documento não pode estar vazio");
        }

        ConsoleUtils.print("Email");
        String email = scanner.nextLine();
        
        if (email.trim().isEmpty()) {
            throw new ValidationException("Email não pode estar vazio");
        }

        ConsoleUtils.print("Telefone");
        String telefone = scanner.nextLine();
        
        if (telefone.trim().isEmpty()) {
            throw new ValidationException("Telefone não pode estar vazio");
        }

        Cliente novoCliente = new Cliente(nome, documento, email, telefone);
        clienteController.save(novoCliente);
        ConsoleUtils.printSuccess("Cliente cadastrado com sucesso!");
    }

    private void listarTodosClientes() {
        ConsoleUtils.println("\n--- Todos os Clientes ---");
        ConsoleUtils.printDivider();
        var clientes = clienteController.getAll();
        if (clientes.isEmpty()) {
            ConsoleUtils.printInfo("Nenhum cliente cadastrado.");
        } else {
            clientes.forEach(cliente -> ConsoleUtils.println(cliente.toString()));
        }
        ConsoleUtils.printDivider();
    }

    private void buscarClientePorId() throws EntityNotFoundException {
        ConsoleUtils.println("\n--- Buscar Cliente por ID ---");
        ConsoleUtils.print("Digite o ID do cliente");
        Long idBusca = scanner.nextLong();
        scanner.nextLine();

        Optional<Cliente> clienteOpt = clienteController.getById(idBusca);
        if (clienteOpt.isPresent()) {
            ConsoleUtils.printDivider();
            ConsoleUtils.println(clienteOpt.get().toString());
            ConsoleUtils.printDivider();
        } else {
            throw new EntityNotFoundException("Cliente", idBusca);
        }
    }

    private void atualizarCliente() throws Exception {
        ConsoleUtils.println("\n--- Atualizar Cliente ---");
        ConsoleUtils.print("Digite o ID do cliente a ser atualizado");
        Long idUpdate = scanner.nextLong();
        scanner.nextLine();

        Optional<Cliente> clienteExistenteOpt = clienteController.getById(idUpdate);
        if (clienteExistenteOpt.isPresent()) {
            Cliente clienteExistente = clienteExistenteOpt.get();
            ConsoleUtils.println("Cliente encontrado. Digite os novos dados (deixe em branco para manter o atual):");
            
            ConsoleUtils.print("Novo Nome (" + clienteExistente.getNome() + ")");
            String novoNome = scanner.nextLine();
            if (!novoNome.trim().isEmpty()) clienteExistente.setNome(novoNome);

            ConsoleUtils.print("Novo Documento (" + clienteExistente.getDocumento() + ")");
            String novoDocumento = scanner.nextLine();
            if (!novoDocumento.trim().isEmpty()) clienteExistente.setDocumento(novoDocumento);

            ConsoleUtils.print("Novo Email (" + clienteExistente.getEmail() + ")");
            String novoEmail = scanner.nextLine();
            if (!novoEmail.trim().isEmpty()) clienteExistente.setEmail(novoEmail);

            ConsoleUtils.print("Novo Telefone (" + clienteExistente.getTelefone() + ")");
            String novoTelefone = scanner.nextLine();
            if (!novoTelefone.trim().isEmpty()) clienteExistente.setTelefone(novoTelefone);

            clienteController.update(idUpdate, clienteExistente);
            ConsoleUtils.printSuccess("Cliente atualizado com sucesso!");
        } else {
            throw new EntityNotFoundException("Cliente", idUpdate);
        }
    }

    private void excluirCliente() throws EntityNotFoundException {
        ConsoleUtils.println("\n--- Excluir Cliente ---");
        ConsoleUtils.print("Digite o ID do cliente a ser excluído");
        Long idDelete = scanner.nextLong();
        scanner.nextLine();

        Optional<Cliente> clienteOpt = clienteController.getById(idDelete);
        if (clienteOpt.isPresent()) {
            if (clienteController.delete(idDelete)) {
                ConsoleUtils.printSuccess("Cliente excluído com sucesso!");
            } else {
                throw new ClienteException("Não foi possível excluir o cliente");
            }
        } else {
            throw new EntityNotFoundException("Cliente", idDelete);
        }
    }
}