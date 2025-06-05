package com.br.Ecoleta.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

@Entity
@Table(name = "pontos_de_coleta")
public class PontoDeColeta extends GenericModel {

    @Column(name = "nome_local", nullable = false, length = 150)
    private String nomeLocal;

    @Column(name = "endereco", nullable = false, length = 255)
    private String endereco;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "pontoDeColeta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Coleta> coletas = new HashSet<>();

    public PontoDeColeta() {
        super();
    }

    public PontoDeColeta(String nomeLocal, String endereco, Double latitude, Double longitude, Cliente cliente) {
        this.nomeLocal = nomeLocal;
        this.endereco = endereco;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cliente = cliente;
    }

    public String getNomeLocal() {
        return nomeLocal;
    }

    public void setNomeLocal(String nomeLocal) {
        this.nomeLocal = nomeLocal;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<Coleta> getColetas() {
        return coletas;
    }

    public void setColetas(Set<Coleta> coletas) {
        this.coletas = coletas;
    }

    public void addColeta(Coleta coleta) {
        this.coletas.add(coleta);
        coleta.setPontoDeColeta(this);
    }

    public void removeColeta(Coleta coleta) {
        this.coletas.remove(coleta);
        coleta.setPontoDeColeta(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PontoDeColeta that = (PontoDeColeta) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getId());
    }

    @Override
    public String toString() {
        return "PontoDeColeta{" +
                "id=" + this.getId() +
                ", nomeLocal='" + nomeLocal + '\'' +
                ", endereco='" + endereco + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", clienteId=" + (cliente != null ? cliente.getId() : "null") +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}