package com.br.Ecoleta.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

@Entity
@Table(name = "motoristas")
public class Motorista extends GenericModel {

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "cpf", unique = true, nullable = false, length = 11)
    private String cpf;

    @Column(name = "cnh", unique = true, nullable = false, length = 20)
    private String cnh;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @OneToMany(mappedBy = "motorista", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rota> rotas = new HashSet<>();

    public Motorista() {
        super();
    }

    public Motorista(String nome, String cpf, String cnh, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.cnh = cnh;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Set<Rota> getRotas() {
        return rotas;
    }

    public void setRotas(Set<Rota> rotas) {
        this.rotas = rotas;
    }

    public void addRota(Rota rota) {
        this.rotas.add(rota);
        rota.setMotorista(this);
    }

    public void removeRota(Rota rota) {
        this.rotas.remove(rota);
        rota.setMotorista(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Motorista motorista = (Motorista) o;
        return Objects.equals(this.getId(), motorista.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getId());
    }

    @Override
    public String toString() {
        return "Motorista{" +
                "id=" + this.getId() +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", cnh='" + cnh + '\'' +
                ", telefone='" + telefone + '\'' +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}