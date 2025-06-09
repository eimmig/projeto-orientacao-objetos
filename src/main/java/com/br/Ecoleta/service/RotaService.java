package com.br.ecoleta.service;

import com.br.ecoleta.model.Coleta;
import com.br.ecoleta.model.Motorista;
import com.br.ecoleta.model.Rota;
import com.br.ecoleta.model.Veiculo;
import com.br.ecoleta.repository.RotaRepository;
import com.br.ecoleta.util.ColetaStatus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RotaService extends GenericService<Rota, Long> {

    public RotaService(RotaRepository repository) {
        super(repository);
    }

    /**
     * Calcula uma rota para o motorista e veículo, pegando coletas PENDENTES até o limite de capacidade do veículo.
     * Cria e retorna uma nova Rota (não salva no banco, apenas retorna o objeto).
     */
    public Rota calcularRotaParaMotorista(Motorista motorista, Veiculo veiculo, ColetaService coletaService) {
        List<Coleta> coletasPendentes = coletaService.getAll().stream()
                .filter(c -> c.getStatus() == ColetaStatus.PENDENTE && c.getRota() == null)
                .toList();
        List<Coleta> coletasSelecionadas = new ArrayList<>();
        double capacidadeRestante = veiculo.getCapacidadeKg();
        for (Coleta coleta : coletasPendentes) {
            if (coleta.getQuantidadeKg() <= capacidadeRestante) {
                coletasSelecionadas.add(coleta);
                capacidadeRestante -= coleta.getQuantidadeKg();
            }
        }
        if (coletasSelecionadas.isEmpty()) return null;
        Rota rota = new Rota(LocalDate.now(), "Rota gerada automaticamente", motorista, veiculo);
        for (Coleta coleta : coletasSelecionadas) {
            rota.addColeta(coleta);
            coleta.setRota(rota);
        }
        return rota;
    }

    /**
     * Busca (ou gera) a rota do dia para o motorista, respeitando a capacidade do veículo.
     * Se já existir uma rota para hoje, retorna ela. Caso contrário, calcula uma nova.
     */
    public Rota buscarOuGerarRotaDoDia(Motorista motorista, Veiculo veiculo, ColetaService coletaService) {
        List<Rota> rotas = this.getAll();
        LocalDate hoje = LocalDate.now();
        for (Rota rota : rotas) {
            if (rota.getMotorista().equals(motorista) && rota.getVeiculo().equals(veiculo) && rota.getDataRota().equals(hoje)) {
                return rota;
            }
        }
        return calcularRotaParaMotorista(motorista, veiculo, coletaService);
    }
}