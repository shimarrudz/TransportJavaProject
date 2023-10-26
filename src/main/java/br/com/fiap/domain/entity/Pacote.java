package br.com.fiap.domain.entity;

public class Pacote extends Transportavel {

    private String etiqueta;

    private Float altura;

    private Float largura;

    private Float profundidade;

    private Float peso;

    private String descricao;

    public Pacote() {
        super("PACOTE");
    }

    public Pacote(String etiqueta, Float altura, Float largura, Float profundidade, Float peso, String descricao) {
        super("PACOTE");
        this.etiqueta = etiqueta;
        this.altura = altura;
        this.largura = largura;
        this.profundidade = profundidade;
        this.peso = peso;
        this.descricao = descricao;
    }

    public Pacote(Long id, String etiqueta, Float altura, Float largura, Float profundidade, Float peso, String descricao) {
        super(id, "PACOTE");
        this.etiqueta = etiqueta;
        this.altura = altura;
        this.largura = largura;
        this.profundidade = profundidade;
        this.peso = peso;
        this.descricao = descricao;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public Pacote setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
        return this;
    }

    public Float getAltura() {
        return altura;
    }

    public Pacote setAltura(Float altura) {
        this.altura = altura;
        return this;
    }

    public Float getLargura() {
        return largura;
    }

    public Pacote setLargura(Float largura) {
        this.largura = largura;
        return this;
    }

    public Float getProfundidade() {
        return profundidade;
    }

    public Pacote setProfundidade(Float profundidade) {
        this.profundidade = profundidade;
        return this;
    }

    public Float getPeso() {
        return peso;
    }

    public Pacote setPeso(Float peso) {
        this.peso = peso;
        return this;
    }

    public String getDescricao() {
        return descricao;
    }

    public Pacote setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    @Override
    public String toString() {
        return "Pacote{" +
                "etiqueta='" + etiqueta + '\'' +
                ", altura=" + altura +
                ", largura=" + largura +
                ", profundidade=" + profundidade +
                ", peso=" + peso +
                ", descricao='" + descricao + '\'' +
                "} " + super.toString();
    }
}
