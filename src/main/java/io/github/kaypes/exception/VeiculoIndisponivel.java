package io.github.kaypes.exception;

public class VeiculoIndisponivel extends SiscoException {
    public VeiculoIndisponivel(String modelo) {
        super("Operação cancelada: O veículo " + modelo + " já foi vendido ou está em manutenção.");
    }
}