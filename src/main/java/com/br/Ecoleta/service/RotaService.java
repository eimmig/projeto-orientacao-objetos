package com.br.ecoleta.service;

import com.br.ecoleta.exception.RotaServiceException;
import com.br.ecoleta.model.Coleta;
import com.br.ecoleta.model.Motorista;
import com.br.ecoleta.model.Rota;
import com.br.ecoleta.model.Veiculo;
import com.br.ecoleta.repository.RotaRepository;
import com.br.ecoleta.util.ColetaStatus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class RotaService extends GenericService<Rota, Long> {
    private static final Logger logger = Logger.getLogger(RotaService.class.getName());
    private final ColetaService coletaService;

    public RotaService(RotaRepository repository, ColetaService coletaService) {
        super(repository);
        this.coletaService = coletaService;
    }

    @Override
    protected String getEntityType() {
        return Rota.class.getName();
    }

    /**
     * Calcula uma rota para o motorista e veículo, pegando coletas PENDENTES até o limite de capacidade do veículo.
     * Cria e retorna uma nova Rota (não salva no banco, apenas retorna o objeto).
     * 
     * @throws RotaServiceException se houver erro ao salvar a rota ou atualizar as coletas
     */
    public Rota calcularRotaParaMotorista(Motorista motorista, Veiculo veiculo) {
        List<Coleta> coletasSelecionadas = selecionarColetasParaRota(veiculo);
        if (coletasSelecionadas.isEmpty()) {
            logger.info(() -> "Nenhuma coleta pendente encontrada para o veículo " + veiculo.getId());
            return null;
        }

        Rota rota = criarESalvarRota(motorista, veiculo, coletasSelecionadas);
        logger.info(() -> "Rota " + rota.getId() + " criada com sucesso para o motorista " + motorista.getId());
        return rota;
    }

    private List<Coleta> selecionarColetasParaRota(Veiculo veiculo) {
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
        
        return coletasSelecionadas;
    }

    private Rota criarESalvarRota(Motorista motorista, Veiculo veiculo, List<Coleta> coletas) {
        Rota rota = new Rota(LocalDate.now(), "Rota gerada automaticamente", motorista, veiculo);
        
        try {
            rota = repository.save(rota);
            associarColetasARota(rota, coletas);
            return rota;
        } catch (Exception e) {
            String mensagem = String.format("Erro ao criar rota para motorista %d e veículo %d", 
                motorista.getId(), veiculo.getId());
            logger.log(Level.SEVERE, mensagem, e);
            throw new RotaServiceException(mensagem, e);
        }
    }

    private void associarColetasARota(Rota rota, List<Coleta> coletas) {
        for (Coleta coleta : coletas) {
            try {
                rota.addColeta(coleta);
                coleta.setRota(rota);
                coletaService.update(coleta.getId(), coleta);
            } catch (RuntimeException e) {
                String mensagem = String.format("Erro ao associar coleta %d à rota %d", 
                    coleta.getId(), rota.getId());
                logger.log(Level.WARNING, mensagem, e);
            }
        }
    }

    /**
     * Busca (ou gera) a rota do dia para o motorista, respeitando a capacidade do veículo.
     * Se já existir uma rota para hoje, retorna ela. Caso contrário, calcula uma nova.
     */
    public Rota buscarOuGerarRotaDoDia(Motorista motorista, Veiculo veiculo) {
        List<Rota> rotas = this.getAll();
        LocalDate hoje = LocalDate.now();
        
        Rota rotaExistente = rotas.stream()
            .filter(r -> r.getMotorista().equals(motorista) && 
                        r.getVeiculo().equals(veiculo) && 
                        r.getDataRota().equals(hoje))
            .findFirst()
            .orElse(null);

        if (rotaExistente != null) {
            return rotaExistente;
        }

        return calcularRotaParaMotorista(motorista, veiculo);
    }

    /**
     * Retorna a lista de coletas pendentes para uma rota específica.
     */
    public List<Coleta> getColetasPendentes(Rota rota) {
        if (rota == null) return List.of();
        RotaRepository rotaRepository = (RotaRepository) this.repository;
        return rotaRepository.findColetasPendentesByRota(rota);
    }
}