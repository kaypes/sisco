package io.github.kaypes;

import io.github.kaypes.exception.VeiculoIndisponivel;
import io.github.kaypes.exception.VeiculoNaoEncontrado;
import io.github.kaypes.model.veiculo.*;
import io.github.kaypes.model.pessoa.*;
import io.github.kaypes.model.financeiro.*;
import io.github.kaypes.exception.SiscoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static void main() {
        var scanner = new Scanner(System.in);

        List<Veiculo> estoque = new ArrayList<>();
        List<Venda> vendasRealizadas = new ArrayList<>();
        List<Pessoa> bancoDeContas = new ArrayList<>();

        bancoDeContas.add(new Usuario("Admin Chefe", "000", "999", "admin@sisco.com", "admin123", PerfilAcesso.ADMINISTRADOR));
        bancoDeContas.add(new Usuario("João Vendedor", "111", "888", "joao@sisco.com", "vend123", PerfilAcesso.VENDEDOR));
        bancoDeContas.add(new Cliente("Maria Oliveira", "123", "777", "maria@gmail.com", "maria123", "Rua A"));

        estoque.add(new Carro("ABC-1234", "Toyota", "Corolla", 2024, "Prata", 0, 150000.0, 4));
        estoque.add(new Moto("DEF-5678", "Yamaha", "MT-09", 2024, "Azul", 0, 60000.0, 900));

        System.out.println("===============================");
        System.out.println("=== BEM-VINDO AO SISCO ===");
        System.out.println("===============================");
        
        while (true) {
            Pessoa usuarioLogado = null;

            while (usuarioLogado == null) {
                System.out.println("\n1. Fazer Login");
                System.out.println("2. Criar Nova Conta");
                System.out.println("0. Desligar o Sistema");
                System.out.print("Escolha: ");
                String opAcesso = scanner.nextLine().trim();

                if (opAcesso.equals("1")) {
                    System.out.print("E-mail: ");
                    String emailLogin = scanner.nextLine().trim();
                    System.out.print("Senha: ");
                    String senhaLogin = scanner.nextLine().trim();

                    for (Pessoa p : bancoDeContas) {
                        if (p.getEmail().equalsIgnoreCase(emailLogin) && p.validarSenha(senhaLogin)) {
                            usuarioLogado = p;
                            break;
                        }
                    }

                    if (usuarioLogado == null) {
                        System.out.println("Erro: E-mail ou senha incorretos! Tente novamente.");
                    } else {
                        System.out.println("\nLogin efetuado com sucesso! Bem-vindo(a), " + usuarioLogado.getNome());
                    }

                } else if (opAcesso.equals("2")) {
                    System.out.println("\n--- TELA DE CADASTRO ---");
                    System.out.println("Qual perfil deseja criar?");
                    System.out.println("1. Funcionário (Vendedor/Admin)");
                    System.out.println("2. Cliente");
                    System.out.print("Escolha: ");
                    String tipoConta = scanner.nextLine().trim();

                    System.out.print("Nome: "); String nome = scanner.nextLine().trim();
                    System.out.print("CPF: "); String cpf = scanner.nextLine().trim();
                    System.out.print("Telefone: "); String tel = scanner.nextLine().trim();
                    System.out.print("E-mail: "); String email = scanner.nextLine().trim();
                    System.out.print("Senha: "); String senha = scanner.nextLine().trim();

                    if (tipoConta.equals("1")) {
                        System.out.print("Perfil (1 para Admin, 2 para Vendedor): ");
                        String perf = scanner.nextLine().trim();
                        PerfilAcesso perfilAcesso = perf.equals("1") ? PerfilAcesso.ADMINISTRADOR : PerfilAcesso.VENDEDOR;

                        bancoDeContas.add(new Usuario(nome, cpf, tel, email, senha, perfilAcesso));
                        System.out.println("Conta de Funcionário criada com sucesso! Faça login para entrar.");

                    } else if (tipoConta.equals("2")) {
                        System.out.print("Endereço Completo: ");
                        String endereco = scanner.nextLine().trim();

                        bancoDeContas.add(new Cliente(nome, cpf, tel, email, senha, endereco));
                        System.out.println("Conta de Cliente criada com sucesso! Faça login para entrar.");
                    } else {
                        System.out.println("Opção inválida.");
                    }
                } else if (opAcesso.equals("0")) {
                    System.out.println("Encerrando o servidor SISCO...");
                    scanner.close();
                    return;
                }
            }

            if (usuarioLogado instanceof Usuario u) {
                u.registrarAcesso();
            }

            int opcao = -1;

            while (opcao != 0) {
                System.out.println("\n--- MENU PRINCIPAL (" + usuarioLogado.getNome() + ") ---");
                System.out.println("1. Listar Estoque");
                System.out.println("3. Simular Financiamento");

                if (usuarioLogado instanceof Usuario) {
                    System.out.println("2. Realizar Venda");
                }
                if (usuarioLogado instanceof Usuario u && u.getPerfil() == PerfilAcesso.ADMINISTRADOR) {
                    System.out.println("4. Ver Faturamento da Concessionária");
                }
                if (usuarioLogado instanceof Cliente) {
                    System.out.println("5. Ver Meu Histórico de Compras");
                }
                System.out.println("0. Fazer Logout");
                System.out.print("Escolha uma opção: ");

                try {
                    opcao = Integer.parseInt(scanner.nextLine().trim());

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
                            if (!(usuarioLogado instanceof Usuario usuarioFuncionario)) {
                                System.out.println("Acesso Negado: Apenas funcionários podem realizar vendas.");
                                break;
                            }

                            System.out.print("E-mail do Cliente comprador: ");
                            String emailComprador = scanner.nextLine().trim();
                            Cliente clienteComprador = null;

                            for (Pessoa p : bancoDeContas) {
                                if (p instanceof Cliente c && p.getEmail().equalsIgnoreCase(emailComprador)) {
                                    clienteComprador = c;
                                    break;
                                }
                            }

                            if (clienteComprador == null) {
                                System.out.println("Erro: Cliente não cadastrado! Peça para ele criar uma conta primeiro.");
                                break;
                            }

                            System.out.print("Digite a PLACA do veículo para vender: ");
                            String placa = scanner.nextLine().trim();

                            Veiculo veiculoEncontrado = getVeiculo(estoque, placa);
                            double valorFinal = FormaPagamento.A_VISTA.calcularValorFinal(veiculoEncontrado.getPrecoVenda());

                            var novaVenda = new Venda(veiculoEncontrado, clienteComprador, usuarioFuncionario, FormaPagamento.A_VISTA, valorFinal);
                            vendasRealizadas.add(novaVenda);
                            clienteComprador.adicionarCompraAoHistorico(novaVenda);

                            System.out.println("\nVenda realizada com sucesso para o cliente: " + clienteComprador.getNome());
                            System.out.println(novaVenda.obterRecibo());
                            break;

                        case 3:
                            System.out.print("Digite a PLACA do carro para simular: ");
                            String placaSimulacao = scanner.nextLine().trim();
                            Veiculo vSimulacao = null;
                            for (Veiculo v : estoque) {
                                if (v.getPlaca().equalsIgnoreCase(placaSimulacao)) {
                                    vSimulacao = v; break;
                                }
                            }
                            if (vSimulacao == null) {
                                throw new VeiculoNaoEncontrado("Veículo não encontrado.");
                            }

                            if (vSimulacao instanceof SimuladorFinanceiro simulador) {
                                System.out.print("Valor da Entrada: R$ ");
                                double entrada = Double.parseDouble(scanner.nextLine().trim());
                                System.out.print("Número de Parcelas (ex: 48): ");
                                int parcelas = Integer.parseInt(scanner.nextLine().trim());
                                Simulacao sim = simulador.simularFinanciamento("Banco Sisco", entrada, parcelas);
                                sim.exibirResumo();
                            } else {
                                System.out.println("Erro: Motos não suportam simulação de financiamento pelo sistema.");
                            }
                            break;

                        case 4:
                            if (!(usuarioLogado instanceof Usuario u) || u.getPerfil() != PerfilAcesso.ADMINISTRADOR) {
                                System.out.println("Acesso Negado: Apenas Administradores podem ver o faturamento.");
                                break;
                            }
                            double fTotal = 0.0;
                            for (Venda v : vendasRealizadas) fTotal += v.getValorFinal();
                            System.out.printf("\nFaturamento Total da Concessionária: R$ %.2f\n", fTotal);
                            break;

                        case 5:
                            if (!(usuarioLogado instanceof Cliente clienteAtual)) {
                                System.out.println("Opção inválida.");
                                break;
                            }
                            System.out.println("\n--- SEU HISTÓRICO DE COMPRAS ---");
                            if (clienteAtual.getHistoricoDeCompras().isEmpty()) {
                                System.out.println("Você ainda não realizou nenhuma compra.");
                            } else {
                                for (Venda v : clienteAtual.getHistoricoDeCompras()) System.out.println(v.obterRecibo());
                            }
                            break;

                        case 0:
                            System.out.println("Fazendo logout...");
                            break;

                        default:
                            System.out.println("Opção inválida.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Erro: Insira apenas números válidos.");
                } catch (SiscoException e) {
                    System.out.println("Erro de Regra de Negócio: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Erro inesperado: " + e.getMessage());
                }
            }
        }
    }

    private static Veiculo getVeiculo(List<Veiculo> estoque, String placa) throws SiscoException {
        Veiculo veiculoEncontrado = null;

        for (Veiculo v : estoque) {
            if (v.getPlaca().equalsIgnoreCase(placa)) { veiculoEncontrado = v; break; }
        }

        if (veiculoEncontrado == null) {
            throw new VeiculoNaoEncontrado("Placa não encontrada.");
        }

        if (veiculoEncontrado.getStatus() != StatusVeiculo.DISPONIVEL) {
            throw new VeiculoIndisponivel("Veículo indisponível.");
        }

        veiculoEncontrado.setStatus(StatusVeiculo.VENDIDO);

        return veiculoEncontrado;
    }
}
