package com.br.ecoleta.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.br.ecoleta.util.TipoVeiculo; // Importa o novo Enum

@Entity
@Table(name = "veiculos")
public class Veiculo extends GenericModel {

    @Column(name = "placa", unique = true, nullable = false, length = 10)
    private String placa;

    @Enumerated(EnumType.STRING)
    @Column(name = "modelo", nullable = false, length = 50)
    private TipoVeiculo modelo;

    @Column(name = "capacidade_kg", nullable = false)
    private Double capacidadeKg;

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rota> rotas = new HashSet<>();

    public Veiculo() {
        super();
    }

    public Veiculo(String placa, TipoVeiculo modelo, Double capacidadeKg) {
        this.placa = placa;
        this.modelo = modelo;
        this.capacidadeKg = capacidadeKg;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public TipoVeiculo getModelo() {
        return modelo;
    }

    public void setModelo(TipoVeiculo modelo) {
        this.modelo = modelo;
    }

    public Double getCapacidadeKg() {
        return capacidadeKg;
    }

    public void setCapacidadeKg(Double capacidadeKg) {
        this.capacidadeKg = capacidadeKg;
    }

    public Set<Rota> getRotas() {
        return rotas;
    }

    public void setRotas(Set<Rota> rotas) {
        this.rotas = rotas;
    }

    public void addRota(Rota rota) {
        this.rotas.add(rota);
        rota.setVeiculo(this);
    }

    public void removeRota(Rota rota) {
        this.rotas.remove(rota);
        rota.setVeiculo(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Veiculo veiculo = (Veiculo) o;
        return Objects.equals(this.getId(), veiculo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getId());
    }

    @Override
    public String toString() {
        return "Veiculo{" +
                "id=" + this.getId() +
                ", placa='" + placa + '\'' +
                ", modelo=" + modelo + // Retorna o nome do Enum
                ", capacidadeKg=" + capacidadeKg +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}