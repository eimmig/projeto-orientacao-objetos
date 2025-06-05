package com.br.Ecoleta.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

@Entity
@Table(name = "clientes")
public class Cliente extends GenericModel {

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "documento", unique = true, nullable = false, length = 20)
    private String documento;

    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PontoDeColeta> pontosDeColeta = new HashSet<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Coleta> coletas = new HashSet<>();

    public Cliente() {
        super();
    }

    public Cliente(String nome, String documento, String email, String telefone) {
        this.nome = nome;
        this.documento = documento;
        this.email = email;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Set<PontoDeColeta> getPontosDeColeta() {
        return pontosDeColeta;
    }

    public void setPontosDeColeta(Set<PontoDeColeta> pontosDeColeta) {
        this.pontosDeColeta = pontosDeColeta;
    }

    public Set<Coleta> getColetas() {
        return coletas;
    }

    public void setColetas(Set<Coleta> coletas) {
        this.coletas = coletas;
    }

    public void addPontoDeColeta(PontoDeColeta ponto) {
        this.pontosDeColeta.add(ponto);
        ponto.setCliente(this);
    }

    public void removePontoDeColeta(PontoDeColeta ponto) {
        this.pontosDeColeta.remove(ponto);
        ponto.setCliente(null);
    }

    public void addColeta(Coleta coleta) {
        this.coletas.add(coleta);
        coleta.setCliente(this);
    }

    public void removeColeta(Coleta coleta) {
        this.coletas.remove(coleta);
        coleta.setCliente(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(this.getId(), cliente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getId());
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + this.getId() +
                ", nome='" + nome + '\'' +
                ", documento='" + documento + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}