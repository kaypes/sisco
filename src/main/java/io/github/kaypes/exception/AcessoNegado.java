package io.github.kaypes.exception;

public class AcessoNegado extends SiscoException {
    public AcessoNegado(String acao) {
        super("Acesso negado: Seu perfil não tem permissão para " + acao + ".");
    }
}