package io.github.kaypes;

// Imports considerando a divisão de pacotes
import io.github.kaypes.model.veiculo.*;
import io.github.kaypes.model.pessoa.*;
import io.github.kaypes.model.financeiro.*;
import io.github.kaypes.exception.SiscoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Veiculo> estoque = new ArrayList<>();
        List<Venda> vendasRealizadas = new ArrayList<>();

        Usuario admin = new Usuario("Admin Chefe", "000.000.000-00", "999", "admin@sisco.com", PerfilAcesso.ADMINISTRADOR);
        Cliente cliente = new Cliente("Maria Oliveira", "123.456.789-00", "777", "maria@gmail.com", "Rua A");

        estoque.add(new Carro("ABC-1234", "Toyota", "Corolla", 2024, "Prata", 0, 150000.0, 4));
        estoque.add(new Moto("DEF-5678", "Yamaha", "MT-09", 2024, "Azul", 0, 60000.0, 900));

        System.out.println("=== INICIANDO O SISTEMA SISCO ===");

        admin.registrarAcesso();
        admin.registrarAcesso("192.168.0.1");

        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Listar Estoque");
            System.out.println("2. Realizar Venda");
            System.out.println("3. Simular Financiamento");
            System.out.println("4. Ver Faturamento");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        System.out.println("\n--- ESTOQUE DISPONÍVEL ---");
                        for (Veiculo v : estoque) {
                            if (v.getStatus() == StatusVeiculo.DISPONIVEL) {
                                System.out.println(v.obterDescricaoDetalhada());
                            }
                        }
                        break;

                    case 2:
                        System.out.print("Digite a PLACA do veículo para vender: ");
                        String placa = scanner.nextLine();

                        Veiculo veiculoEncontrado = null;
                        for (Veiculo v : estoque) {
                            if (v.getPlaca().equalsIgnoreCase(placa)) {
                                veiculoEncontrado = v;
                                break;
                            }
                        }

                        if (veiculoEncontrado == null) {
                            throw new SiscoException("Veículo não encontrado com a placa: " + placa);
                        }
                        if (veiculoEncontrado.getStatus() != StatusVeiculo.DISPONIVEL) {
                            throw new SiscoException("Este veículo já foi vendido ou está indisponível.");
                        }

                        veiculoEncontrado.setStatus(StatusVeiculo.VENDIDO);

                        double valorFinal = FormaPagamento.A_VISTA.calcularValorFinal(veiculoEncontrado.getPrecoVenda());

                        Venda novaVenda = new Venda(veiculoEncontrado, cliente, admin, FormaPagamento.A_VISTA, valorFinal);
                        vendasRealizadas.add(novaVenda);
                        cliente.adicionarCompraAoHistorico(novaVenda);

                        System.out.println("\nVenda realizada com sucesso!");
                        System.out.println(novaVenda.obterRecibo());
                        break;

                    case 3:
                        System.out.print("Digite a PLACA do carro para simular: ");
                        String placaSimulacao = scanner.nextLine();

                        Veiculo vSimulacao = null;
                        for (Veiculo v : estoque) {
                            if (v.getPlaca().equalsIgnoreCase(placaSimulacao)) {
                                vSimulacao = v;
                                break;
                            }
                        }

                        if (vSimulacao == null) {
                            throw new SiscoException("Veículo não encontrado.");
                        }

                        if (vSimulacao instanceof SimuladorFinanceiro) {
                            SimuladorFinanceiro simulador = (SimuladorFinanceiro) vSimulacao;

                            System.out.print("Valor da Entrada: R$ ");
                            double entrada = Double.parseDouble(scanner.nextLine());
                            System.out.print("Número de Parcelas (ex: 48): ");
                            int parcelas = Integer.parseInt(scanner.nextLine());

                            Simulacao sim = simulador.simularFinanciamento("Banco Sisco", entrada, parcelas);
                            sim.exibirResumo();
                        } else {
                            System.out.println("Erro: Este tipo de veículo (Moto) não suporta simulação de financiamento pelo sistema.");
                        }
                        break;

                    case 4:
                        double faturamentoTotal = 0.0;
                        for (Venda v : vendasRealizadas) {
                            faturamentoTotal += v.getValorFinal();
                        }
                        System.out.printf("\n💰 Faturamento Total da Concessionária: R$ %.2f\n", faturamentoTotal);
                        break;

                    case 0:
                        System.out.println("Encerrando o sistema...");
                        break;

                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro de digitação: Por favor, insira apenas números válidos.");
            } catch (IllegalArgumentException e) {
                System.out.println("Erro de Validação: " + e.getMessage());
            } catch (SiscoException e) {
                System.out.println("Erro de Regra de Negócio: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }

        scanner.close();
    }
}