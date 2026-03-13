package io.github.kaypes.model.financeiro;

public enum FormaPagamento {
    A_VISTA {
        @Override
        public double calcularValorFinal(double valorBase) {
            return valorBase * 0.90;
        }
    },
    FINANCIAMENTO {
        @Override
        public double calcularValorFinal(double valorBase) {
            return valorBase * 0.90;
        }
    };

    public abstract double calcularValorFinal(double valorBase);
}
