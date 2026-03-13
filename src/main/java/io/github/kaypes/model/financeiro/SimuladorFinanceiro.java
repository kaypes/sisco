package io.github.kaypes.model.financeiro;

public interface SimuladorFinanceiro {
    Simulacao simularFinanciamento(String banco, double valorEntrada, int numeroParcelas);
}
