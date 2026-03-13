package io.github.kaypes.model;

public record Simulacao(String banco, double valorEntrada, int parcelas, double valorParcela, double totalFinanciado) {
    public void exibirResumo() {
        System.out.printf("Banco: %s | Entrada: R%.2f | %dx de R%.2f | Total: R%.2f\n",
                banco, valorEntrada, parcelas, valorParcela, totalFinanciado);
    }
}
