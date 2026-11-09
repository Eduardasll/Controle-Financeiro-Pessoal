package br.com.portfolio.financeiro;

import br.com.portfolio.financeiro.model.Movimentacao;
import br.com.portfolio.financeiro.model.TipoMovimentacao;
import br.com.portfolio.financeiro.repository.MovimentacaoRepository;
import br.com.portfolio.financeiro.service.FinanceiroService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * Camada de interface com o usuario (terminal).
 * Responsavel por exibir o menu, ler entradas e imprimir resultados.
 * Nao calcula saldo nem aplica regras: isso fica no FinanceiroService.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final FinanceiroService service =
            new FinanceiroService(new MovimentacaoRepository());

    public static void main(String[] args) {
        boolean executando = true;
        while (executando) {
            exibirMenu();
            String opcao = scanner.nextLine().trim();
            switch (opcao) {
                case "1" -> cadastrar(TipoMovimentacao.RECEITA);
                case "2" -> cadastrar(TipoMovimentacao.DESPESA);
                case "3" -> listar();
                case "4" -> verSaldo();
                case "5" -> filtrarPorCategoria();
                case "6" -> excluir();
                case "7" -> verResumo();
                case "0" -> executando = false;
                default -> System.out.println("Opcao invalida. Tente novamente.");
            }
            System.out.println();
        }
        System.out.println("Ate logo!");
    }

    private static void exibirMenu() {
        System.out.println("===== CONTROLE FINANCEIRO =====");
        System.out.println("1 - Cadastrar receita");
        System.out.println("2 - Cadastrar despesa");
        System.out.println("3 - Listar movimentacoes");
        System.out.println("4 - Ver saldo");
        System.out.println("5 - Filtrar por categoria");
        System.out.println("6 - Excluir movimentacao");
        System.out.println("7 - Ver resumo");
        System.out.println("0 - Sair");
        System.out.print("Escolha: ");
    }

    private static void cadastrar(TipoMovimentacao tipo) {
        System.out.print("Descricao: ");
        String descricao = scanner.nextLine();

        System.out.print("Valor: ");
        String valorTexto = scanner.nextLine().trim().replace(",", ".");

        System.out.print("Categoria: ");
        String categoria = scanner.nextLine();

        BigDecimal valor;
        try {
            valor = new BigDecimal(valorTexto);
        } catch (NumberFormatException e) {
            System.out.println("Valor invalido. Use apenas numeros (ex: 150.00).");
            return;
        }

        try {
            Movimentacao m = service.cadastrar(descricao, valor, tipo, categoria);
            System.out.println("Cadastrada com sucesso: " + m);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listar() {
        List<Movimentacao> movimentacoes = service.listar();
        if (movimentacoes.isEmpty()) {
            System.out.println("Nenhuma movimentacao cadastrada.");
            return;
        }
        System.out.println("--- Movimentacoes ---");
        for (Movimentacao m : movimentacoes) {
            System.out.println(m);
        }
    }

    private static void verSaldo() {
        System.out.println("Saldo atual: R$ " + service.calcularSaldo());
    }

    private static void filtrarPorCategoria() {
        System.out.print("Categoria: ");
        String categoria = scanner.nextLine();
        List<Movimentacao> movimentacoes = service.filtrarPorCategoria(categoria);
        if (movimentacoes.isEmpty()) {
            System.out.println("Nenhuma movimentacao nessa categoria.");
            return;
        }
        System.out.println("--- Movimentacoes da categoria " + categoria + " ---");
        for (Movimentacao m : movimentacoes) {
            System.out.println(m);
        }
    }

    private static void excluir() {
        System.out.print("ID para excluir: ");
        String idTexto = scanner.nextLine().trim();
        int id;
        try {
            id = Integer.parseInt(idTexto);
        } catch (NumberFormatException e) {
            System.out.println("ID invalido. Digite um numero.");
            return;
        }
        if (service.excluirPorId(id)) {
            System.out.println("Movimentacao excluida com sucesso.");
        } else {
            System.out.println("Movimentacao nao encontrada.");
        }
    }

    private static void verResumo() {
        System.out.println("--- Resumo ---");
        System.out.println("Total de receitas: R$ " + service.totalDeReceitas());
        System.out.println("Total de despesas: R$ " + service.totalDeDespesas());
        System.out.println("Saldo:             R$ " + service.calcularSaldo());
    }
}
