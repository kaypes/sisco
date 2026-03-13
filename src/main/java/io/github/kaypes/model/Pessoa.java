package io.github.kaypes.model;

public class Pessoa {
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    public Pessoa(String nome, String cpf, String telefone, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }
}
