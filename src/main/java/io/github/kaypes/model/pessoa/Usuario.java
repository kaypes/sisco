package io.github.kaypes.model.pessoa;

public class Usuario extends Pessoa {
    private PerfilAcesso perfil;

    public Usuario(String nome, String cpf, String telefone, String email, String senha, PerfilAcesso perfil) {
        super(nome, cpf, telefone, senha, email);
        this.perfil = perfil;
    }

    public PerfilAcesso getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilAcesso perfil) {
        this.perfil = perfil;
    }

    public boolean temPermissaoGerencial() {
        return perfil == PerfilAcesso.ADMINISTRADOR || perfil == PerfilAcesso.GERENTE_FINANCEIRO;
    }

    public void registrarAcesso() {
        System.out.println("Login: " + getNome() + " (" + perfil + ")");
    }

    public void registrarAcesso(String ip) {
        System.out.println("Login: " + getNome() + " (" + perfil + ") via IP: " + ip);
    }
}
