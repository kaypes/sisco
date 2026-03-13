package io.github.kaypes.model.pessoa;

public class Usuario extends Pessoa {
    private PerfilAcesso perfil;

    public Usuario(String nome, String cpf, String telefone, String email, PerfilAcesso perfil) {
        super(nome, cpf, telefone, email);
        this.perfil = perfil;
    }

    public PerfilAcesso getPerfil() {
        return perfil;
    }

    public boolean temPermissaoGerencial() {
        return perfil == PerfilAcesso.ADMINISTRADOR || perfil == PerfilAcesso.GERENTE_FINANCEIRO;
    }
}
