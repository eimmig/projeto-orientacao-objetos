package com.br.ecoleta.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

@Entity
@Table(name = "rotas")
public class Rota extends GenericModel {

    @Column(name = "data_rota", nullable = false)
    private LocalDate dataRota;

    @Column(name = "observacoes", length = 500)
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motorista_id", nullable = false)
    private Motorista motorista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @OneToMany(mappedBy = "rota", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Coleta> coletas = new HashSet<>();

    public Rota() {
        super();
    }

    public Rota(LocalDate dataRota, String observacoes, Motorista motorista, Veiculo veiculo) {
        this.dataRota = dataRota;
        this.observacoes = observacoes;
        this.motorista = motorista;
        this.veiculo = veiculo;
    }

    public LocalDate getDataRota() {
        return dataRota;
    }

    public void setDataRota(LocalDate dataRota) {
        this.dataRota = dataRota;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Set<Coleta> getColetas() {
        return coletas;
    }

    public void setColetas(Set<Coleta> coletas) {
        this.coletas = coletas;
    }

    public void addColeta(Coleta coleta) {
        this.coletas.add(coleta);
        coleta.setRota(this);
    }

    public void removeColeta(Coleta coleta) {
        this.coletas.remove(coleta);
        coleta.setRota(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Rota rota = (Rota) o;
        return Objects.equals(this.getId(), rota.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getId());
    }

    @Override
    public String toString() {
        return "Rota{" +
                "id=" + this.getId() +
                ", dataRota=" + dataRota +
                ", observacoes='" + observacoes + '\'' +
                ", motoristaId=" + (motorista != null ? motorista.getId() : "null") +
                ", veiculoId=" + (veiculo != null ? veiculo.getId() : "null") +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}