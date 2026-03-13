package io.github.kaypes.model.veiculo;

import io.github.kaypes.model.financeiro.Simulacao;
import io.github.kaypes.model.financeiro.SimuladorFinanceiro;

public class Carro extends Veiculo implements SimuladorFinanceiro {
    private int numeroPortas;

    public Carro(String placa, String marca, String modelo, int anoFabricacao, String cor, double km, double preco, int numeroPortas) {
        super(placa, marca, modelo, anoFabricacao, cor, km, preco);
        this.numeroPortas = numeroPortas;
    }

    @Override
    public String obterDescricaoDetalhada() {
        return String.format("Carro [%s]: %s %s | Portas: %d | R$%.2f",
                getPlaca(), getMarca(), getModelo(), numeroPortas, getPrecoVenda());
    }

    @Override
    public Simulacao simularFinanciamento(String banco, double valorEntrada, int numeroParcelas) {
        if (valorEntrada >= getPrecoVenda()) {
            throw new IllegalArgumentException("A entrada não pode ser maior ou igual o valor do carro");
        }

        double saldoDevedor = getPrecoVenda() - valorEntrada;
        double taxaJuros = numeroParcelas > 24 ? 1.08 : 1.05;

        double valorTotalComJuros = saldoDevedor * taxaJuros;
        double valorParcela = valorTotalComJuros / numeroParcelas;

        return new Simulacao(banco, valorEntrada, numeroParcelas, valorParcela, valorTotalComJuros + valorEntrada);
    }
}
