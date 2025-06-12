package com.br.ecoleta.factory;

import com.br.ecoleta.controller.*;
import com.br.ecoleta.service.RotaService;
import com.br.ecoleta.view.*;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class ViewFactory {
    private final Map<Class<?>, Object> views;
    private final Scanner scanner;
    private final ClienteController clienteController;
    private final PontoDeColetaController pontoDeColetaController;
    private final VeiculoController veiculoController;
    private final MotoristaController motoristaController;
    private final RotaController rotaController;
    private final ColetaController coletaController;
    private final RotaService rotaService;

    private ViewFactory(Builder builder) {
        this.scanner = builder.scanner;
        this.clienteController = builder.clienteController;
        this.pontoDeColetaController = builder.pontoDeColetaController;
        this.veiculoController = builder.veiculoController;
        this.motoristaController = builder.motoristaController;
        this.rotaController = builder.rotaController;
        this.coletaController = builder.coletaController;
        this.rotaService = builder.rotaService;
        this.views = new HashMap<>();
        initializeViews();
    }

    private void initializeViews() {
        views.put(ClienteView.class, new ClienteView(clienteController, scanner));
        views.put(PontoDeColetaView.class, new PontoDeColetaView(pontoDeColetaController, clienteController, scanner));
        views.put(VeiculoView.class, new VeiculoView(veiculoController, scanner));
        views.put(MotoristaView.class, new MotoristaView(motoristaController, veiculoController, rotaService, scanner));
        views.put(RotaView.class, new RotaView(rotaController, motoristaController, veiculoController, scanner));
        views.put(ColetaView.class, new ColetaView(coletaController, clienteController, pontoDeColetaController, rotaController, scanner));
        
        views.put(MenuView.class, new MenuView(
            getView(ClienteView.class),
            getView(PontoDeColetaView.class),
            getView(VeiculoView.class),
            getView(MotoristaView.class),
            getView(RotaView.class),
            getView(ColetaView.class),
            scanner
        ));
    }

    @SuppressWarnings("unchecked")
    public <T> T getView(Class<T> viewClass) {
        return (T) views.get(viewClass);
    }

    public static class Builder {
        private Scanner scanner;
        private ClienteController clienteController;
        private PontoDeColetaController pontoDeColetaController;
        private VeiculoController veiculoController;
        private MotoristaController motoristaController;
        private RotaController rotaController;
        private ColetaController coletaController;
        private RotaService rotaService;

        public Builder scanner(Scanner scanner) {
            this.scanner = scanner;
            return this;
        }

        public Builder clienteController(ClienteController clienteController) {
            this.clienteController = clienteController;
            return this;
        }

        public Builder pontoDeColetaController(PontoDeColetaController pontoDeColetaController) {
            this.pontoDeColetaController = pontoDeColetaController;
            return this;
        }

        public Builder veiculoController(VeiculoController veiculoController) {
            this.veiculoController = veiculoController;
            return this;
        }

        public Builder motoristaController(MotoristaController motoristaController) {
            this.motoristaController = motoristaController;
            return this;
        }

        public Builder rotaController(RotaController rotaController) {
            this.rotaController = rotaController;
            return this;
        }

        public Builder coletaController(ColetaController coletaController) {
            this.coletaController = coletaController;
            return this;
        }

        public Builder rotaService(RotaService rotaService) {
            this.rotaService = rotaService;
            return this;
        }

        public ViewFactory build() {
            return new ViewFactory(this);
        }
    }
}
