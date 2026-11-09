package br.com.portfolio.financeiro.service;

import br.com.portfolio.financeiro.model.Movimentacao;
import br.com.portfolio.financeiro.model.TipoMovimentacao;
import br.com.portfolio.financeiro.repository.MovimentacaoRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Contem as regras de negocio do controle financeiro:
 * cadastro, validacoes, exclusao, filtro e calculo de saldo.
 * Nao conversa com o usuario (isso e responsabilidade da Main).
 */
public class FinanceiroService {

    private final MovimentacaoRepository repository;

    public FinanceiroService(MovimentacaoRepository repository) {
        this.repository = repository;
    }

    /**
     * Cadastra uma nova movimentacao apos validar os dados.
     * Lanca IllegalArgumentException quando algum dado for invalido.
     */
    public Movimentacao cadastrar(String descricao, BigDecimal valor,
                                  TipoMovimentacao tipo, String categoria) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descricao nao pode ser vazia.");
        }
        if (categoria == null || categoria.isBlank()) {
            throw new IllegalArgumentException("A categoria nao pode ser vazia.");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor deve ser maior que zero.");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("O tipo e obrigatorio.");
        }

        int id = repository.gerarId();
        Movimentacao movimentacao =
                new Movimentacao(id, descricao.trim(), valor, tipo, categoria.trim());
        repository.adicionar(movimentacao);
        return movimentacao;
    }

    public List<Movimentacao> listar() {
        return repository.listar();
    }

    public List<Movimentacao> filtrarPorCategoria(String categoria) {
        List<Movimentacao> encontradas = new ArrayList<>();
        if (categoria == null) {
            return encontradas;
        }
        for (Movimentacao m : repository.listar()) {
            if (m.getCategoria().equalsIgnoreCase(categoria.trim())) {
                encontradas.add(m);
            }
        }
        return encontradas;
    }

    public boolean excluirPorId(int id) {
        return repository.removerPorId(id);
    }

    public BigDecimal totalDeReceitas() {
        return somarPorTipo(TipoMovimentacao.RECEITA);
    }

    public BigDecimal totalDeDespesas() {
        return somarPorTipo(TipoMovimentacao.DESPESA);
    }

    /** Saldo = total de receitas - total de despesas. Pode ser negativo. */
    public BigDecimal calcularSaldo() {
        return totalDeReceitas().subtract(totalDeDespesas());
    }

    private BigDecimal somarPorTipo(TipoMovimentacao tipo) {
        BigDecimal total = BigDecimal.ZERO;
        for (Movimentacao m : repository.listar()) {
            if (m.getTipo() == tipo) {
                total = total.add(m.getValor());
            }
        }
        return total;
    }
}
