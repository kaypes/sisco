package io.github.kaypes.exception;

public class VeiculoNaoEncontrado extends SiscoException {
    public VeiculoNaoEncontrado(String placa) {
        super("Falha na busca: Nenhum veículo encontrado com a placa " + placa + ".");
    }
}