package io.github.kaypes.model.veiculo;

public abstract class Veiculo {
    private String placa;
    private String marca;
    private String modelo;
    private int anoFabricacao;
    private String cor;
    private double quilometragem;
    private double precoVenda;
    private StatusVeiculo status;

    public Veiculo(String placa, String marca, String modelo, int anoFabricacao, String cor, double quilometragem, double precoVenda) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.anoFabricacao = anoFabricacao;
        this.cor = cor;
        this.quilometragem = quilometragem;
        this.precoVenda = precoVenda;
        this.status = StatusVeiculo.DISPONIVEL;
    }

    public abstract String obterDescricaoDetalhada();

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public String getMarca() {
        return marca;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public StatusVeiculo getStatus() {
        return status;
    }

    public void setStatus(StatusVeiculo status) {
        this.status = status;
    }
}
