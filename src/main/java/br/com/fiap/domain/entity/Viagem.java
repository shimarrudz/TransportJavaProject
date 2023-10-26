package br.com.fiap.domain.entity;

import br.com.fiap.infra.security.entity.Pessoa;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Viagem {

    private Long id;
    private String origem;

    private String destino;

    private Pessoa cliente;

    private LocalDateTime saida;

    private LocalDateTime chegada;

    private BigDecimal valor;

    private Set<Transportavel> transportaveis = new LinkedHashSet<>();


    public Viagem() {
    }

    public Viagem(Long id, String origem, String destino, Pessoa cliente, LocalDateTime saida, LocalDateTime chegada, BigDecimal valor, Set<Transportavel> transportaveis) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.cliente = cliente;
        this.saida = saida;
        this.chegada = chegada;
        this.valor = valor;
        this.transportaveis = Objects.nonNull(transportaveis) ? transportaveis : new LinkedHashSet<>();
    }

    public Viagem addTransportavel(Transportavel t) {
        transportaveis.add(t);
        return this;
    }

    public Viagem removeTransportavel(Transportavel t) {
        transportaveis.remove(t);
        return this;
    }

    public Long getId() {
        return id;
    }

    public Viagem setId(Long id) {
        this.id = id;
        return this;
    }

    public String getOrigem() {
        return origem;
    }

    public Viagem setOrigem(String origem) {
        this.origem = origem;
        return this;
    }

    public String getDestino() {
        return destino;
    }

    public Viagem setDestino(String destino) {
        this.destino = destino;
        return this;
    }

    public Pessoa getCliente() {
        return cliente;
    }

    public Viagem setCliente(Pessoa cliente) {
        this.cliente = cliente;
        return this;
    }

    public LocalDateTime getSaida() {
        return saida;
    }

    public Viagem setSaida(LocalDateTime saida) {
        this.saida = saida;
        return this;
    }

    public LocalDateTime getChegada() {
        return chegada;
    }

    public Viagem setChegada(LocalDateTime chegada) {
        this.chegada = chegada;
        return this;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Viagem setValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public Set<Transportavel> getTransportaveis() {
        return Collections.unmodifiableSet(transportaveis);
    }


    @Override
    public String toString() {
        return "Viagem{" +
                "id='" + id + '\'' +
                "origem='" + origem + '\'' +
                ", destino='" + destino + '\'' +
                ", cliente=" + cliente +
                ", saida=" + saida +
                ", chegada=" + chegada +
                ", valor=" + valor +
                ", transportaveis=" + transportaveis +
                '}';
    }


}
