package br.com.fiap.domain.entity;

public class Transportavel {

    private Long id;

    private String tipo;


    public Transportavel() {
    }

    public Transportavel(String tipo) {
        this.tipo = tipo;
    }

    public Transportavel(Long id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public Transportavel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTipo() {
        return tipo;
    }

    public Transportavel setTipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    @Override
    public String toString() {
        return "Transportavel{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
