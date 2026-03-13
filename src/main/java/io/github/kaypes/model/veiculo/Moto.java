package io.github.kaypes.model.veiculo;

public class Moto extends Veiculo {
    private int cilindradas;

    public Moto(String placa, String marca, String modelo, int anoFabricacao, String cor, double km, double preco, int cilindradas) {
        super(placa, marca, modelo, anoFabricacao, cor, km, preco);
        this.cilindradas = cilindradas;
    }

    @Override
    public String obterDescricaoDetalhada() {
        return String.format("Moto [%s]: %s %s | %dcc | R$%.2f",
                getPlaca(), getMarca(), getModelo(), cilindradas, getPrecoVenda());
    }
}
