package io.github.kaypes.model.pessoa;

import io.github.kaypes.model.financeiro.Venda;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Pessoa {
    private String endereco;
    private List<Venda> historicoDeCompras;

    public Cliente(String nome, String cpf, String telefone, String email, String senha, String endereco) {
        super(nome, cpf, telefone, email, senha);
        this.endereco = endereco;
        this.historicoDeCompras = new ArrayList<>();
    }

    public void adicionarCompraAoHistorico(Venda venda) {
        this.historicoDeCompras.add(venda);
    }

    public List<Venda> getHistoricoDeCompras() {
        return historicoDeCompras;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
