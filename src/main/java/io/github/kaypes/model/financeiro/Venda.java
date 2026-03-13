package io.github.kaypes.model.financeiro;

import io.github.kaypes.model.pessoa.Cliente;
import io.github.kaypes.model.pessoa.Usuario;
import io.github.kaypes.model.veiculo.Veiculo;

import java.time.LocalDateTime;
import java.util.UUID;

public class Venda {
    private String idVenda;
    private Veiculo veiculo;
    private Cliente cliente;
    private Usuario vendedor;
    private FormaPagamento formaPagamento;
    private LocalDateTime dataVenda;
    private double valorFinal;

    public Venda(Veiculo veiculo, Cliente cliente, Usuario vendedor, FormaPagamento formaPagamento, double valorFinal) {
        this.idVenda = UUID.randomUUID().toString();
        this.veiculo = veiculo;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.formaPagamento = formaPagamento;
        this.dataVenda = LocalDateTime.now();
        this.valorFinal = valorFinal;
    }

    public String obterRecibo() {
        return String.format("Recibo [%s] | %s | %s | %s | R$%.2f | Data: %s",
                idVenda.substring(0, 8), cliente.getNome(), veiculo.getModelo(), formaPagamento, valorFinal, dataVenda.toLocalDate());
    }

    public double getValorFinal() {
        return valorFinal;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public Usuario getVendedor() {
        return vendedor;
    }
}
