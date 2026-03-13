package io.github.kaypes.model;

public interface SimuladorFinanceiro {
    Simulacao simularFinanciamento(String banco, double valorEntrada, int numeroParcelas);
}
