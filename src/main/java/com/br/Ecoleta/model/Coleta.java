package com.br.ecoleta.model;

import com.br.ecoleta.util.ColetaStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "coletas")
public class Coleta extends GenericModel {

    @Column(name = "data_hora_coleta", nullable = false)
    private LocalDateTime dataHoraColeta;

    @Column(name = "quantidade_kg", nullable = false)
    private Double quantidadeKg;

    @Column(name = "observacoes", length = 500)
    private String observacoes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ColetaStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ponto_de_coleta_id", nullable = false)
    private PontoDeColeta pontoDeColeta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rota_id")
    private Rota rota;

    public Coleta() {
        super();
    }

    public Coleta(LocalDateTime dataHoraColeta, Double quantidadeKg, String observacoes, ColetaStatus status, Cliente cliente, PontoDeColeta pontoDeColeta, Rota rota) {
        this.dataHoraColeta = dataHoraColeta;
        this.quantidadeKg = quantidadeKg;
        this.observacoes = observacoes;
        this.status = status;
        this.cliente = cliente;
        this.pontoDeColeta = pontoDeColeta;
        this.rota = rota;
    }

    public LocalDateTime getDataHoraColeta() {
        return dataHoraColeta;
    }

    public void setDataHoraColeta(LocalDateTime dataHoraColeta) {
        this.dataHoraColeta = dataHoraColeta;
    }

    public Double getQuantidadeKg() {
        return quantidadeKg;
    }

    public void setQuantidadeKg(Double quantidadeKg) {
        this.quantidadeKg = quantidadeKg;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public ColetaStatus getStatus() {
        return status;
    }

    public void setStatus(ColetaStatus status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public PontoDeColeta getPontoDeColeta() {
        return pontoDeColeta;
    }

    public void setPontoDeColeta(PontoDeColeta pontoDeColeta) {
        this.pontoDeColeta = pontoDeColeta;
    }

    public Rota getRota() {
        return rota;
    }

    public void setRota(Rota rota) {
        this.rota = rota;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Coleta coleta = (Coleta) o;
        return Objects.equals(this.getId(), coleta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getId());
    }

    @Override
    public String toString() {
        return "Coleta{" +
                "id=" + this.getId() +
                ", dataHoraColeta=" + dataHoraColeta +
                ", quantidadeKg=" + quantidadeKg +
                ", observacoes='" + observacoes + '\'' +
                ", status=" + status +
                ", clienteId=" + (cliente != null ? cliente.getId() : "null") +
                ", pontoDeColetaId=" + (pontoDeColeta != null ? pontoDeColeta.getId() : "null") +
                ", rotaId=" + (rota != null ? rota.getId() : "null") +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}