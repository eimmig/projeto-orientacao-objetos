package com.br.ecoleta.view;

import com.br.ecoleta.controller.ClienteController;
import com.br.ecoleta.controller.PontoDeColetaController;
import com.br.ecoleta.model.Cliente;
import com.br.ecoleta.model.PontoDeColeta;
import java.util.Optional;
import java.util.Scanner;

public class PontoDeColetaView {
    private final PontoDeColetaController pontoDeColetaController;
    private final ClienteController clienteController;
    private final Scanner scanner;

    public PontoDeColetaView(PontoDeColetaController pontoDeColetaController, ClienteController clienteController, Scanner scanner) {
        this.pontoDeColetaController = pontoDeColetaController;
        this.clienteController = clienteController;
        this.scanner = scanner;
    }

    public void exibirMenuPontoDeColeta() {
        int subOpcao;
        do {
            System.out.println("\n--- Gerenciar Pontos de Coleta ---");
            System.out.println("1. Cadastrar Ponto de Coleta");
            System.out.println("2. Listar Todos os Pontos de Coleta");
            System.out.println("3. Buscar Ponto de Coleta por ID");
            System.out.println("4. Atualizar Ponto de Coleta");
            System.out.println("5. Excluir Ponto de Coleta");
            System.out.println("0. Voltar ao Menu Principal");

            System.out.print("Opção: ");
            subOpcao = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (subOpcao) {
                    case 1:
                        cadastrarPontoDeColeta();
                        break;
                    case 2:
                        listarTodosPontosDeColeta();
                        break;
                    case 3:
                        buscarPontoDeColetaPorId();
                        break;
                    case 4:
                        atualizarPontoDeColeta();
                        break;
                    case 5:
                        excluirPontoDeColeta();
                        break;
                    case 0:
                        System.out.println("Voltando ao Menu Principal.");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (Exception e) {
                System.err.println("Erro na operação de pontos de coleta: " + e.getMessage());
            }
        } while (subOpcao != 0);
    }

    private void cadastrarPontoDeColeta() throws Exception {
        System.out.println("\n--- Cadastro de Ponto de Coleta ---");
        System.out.print("Nome do Local: ");
        String nomeLocal = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Latitude: ");
        Double latitude = scanner.nextDouble();
        System.out.print("Longitude: ");
        Double longitude = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("ID do Cliente responsável: ");
        Long clienteId = scanner.nextLong();
        scanner.nextLine();

        Optional<Cliente> clienteOpt = clienteController.getById(clienteId);
        if (clienteOpt.isPresent()) {
            PontoDeColeta novoPonto = new PontoDeColeta(nomeLocal, endereco, latitude, longitude, clienteOpt.get());
            pontoDeColetaController.save(novoPonto);
        } else {
            System.out.println("Cliente com ID " + clienteId + " não encontrado. Operação cancelada.");
        }
    }

    private void listarTodosPontosDeColeta() {
        System.out.println("\n--- Todos os Pontos de Coleta ---");
        pontoDeColetaController.getAll();
    }

    private void buscarPontoDeColetaPorId() {
        System.out.println("\n--- Buscar Ponto de Coleta por ID ---");
        System.out.print("Digite o ID do ponto de coleta: ");
        Long idBusca = scanner.nextLong();
        scanner.nextLine();
        pontoDeColetaController.getById(idBusca);
    }

    private void atualizarPontoDeColeta() throws Exception {
        System.out.println("\n--- Atualizar Ponto de Coleta ---");
        System.out.print("Digite o ID do ponto de coleta a ser atualizado: ");
        Long idUpdate = scanner.nextLong();
        scanner.nextLine();

        Optional<PontoDeColeta> pontoExistenteOpt = pontoDeColetaController.getById(idUpdate);
        if (pontoExistenteOpt.isPresent()) {
            PontoDeColeta pontoExistente = pontoExistenteOpt.get();
            System.out.println("Ponto de Coleta encontrado. Digite os novos dados (deixe em branco para manter o atual):");

            System.out.print("Novo Nome do Local (" + pontoExistente.getNomeLocal() + "): ");
            String novoNomeLocal = scanner.nextLine();
            if (!novoNomeLocal.trim().isEmpty()) pontoExistente.setNomeLocal(novoNomeLocal);

            System.out.print("Novo Endereço (" + pontoExistente.getEndereco() + "): ");
            String novoEndereco = scanner.nextLine();
            if (!novoEndereco.trim().isEmpty()) pontoExistente.setEndereco(novoEndereco);

            System.out.print("Nova Latitude (" + pontoExistente.getLatitude() + "): ");
            String latStr = scanner.nextLine();
            if (!latStr.trim().isEmpty()) pontoExistente.setLatitude(Double.parseDouble(latStr));

            System.out.print("Nova Longitude (" + pontoExistente.getLongitude() + "): ");
            String longStr = scanner.nextLine();
            if (!longStr.trim().isEmpty()) pontoExistente.setLongitude(Double.parseDouble(longStr));

            System.out.print("Novo ID do Cliente (" + pontoExistente.getCliente().getId() + "): ");
            String clienteIdStr = scanner.nextLine();
            if (!clienteIdStr.trim().isEmpty()) {
                Long novoClienteId = Long.parseLong(clienteIdStr);
                Optional<Cliente> novoClienteOpt = clienteController.getById(novoClienteId);
                if (novoClienteOpt.isPresent()) {
                    pontoExistente.setCliente(novoClienteOpt.get());
                } else {
                    System.out.println("Cliente com ID " + novoClienteId + " não encontrado. Cliente não será atualizado.");
                }
            }

            pontoDeColetaController.update(idUpdate, pontoExistente);
        } else {
            System.out.println("Ponto de Coleta com ID " + idUpdate + " não encontrado.");
        }
    }

    private void excluirPontoDeColeta() {
        System.out.println("\n--- Excluir Ponto de Coleta ---");
        System.out.print("Digite o ID do ponto de coleta a ser excluído: ");
        Long idDelete = scanner.nextLong();
        scanner.nextLine();
        pontoDeColetaController.delete(idDelete);
    }
}