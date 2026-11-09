package br.com.portfolio.financeiro.repository;

import br.com.portfolio.financeiro.model.Movimentacao;

import java.util.ArrayList;
import java.util.List;

/**
 * Guarda as movimentacoes em memoria usando uma lista.
 * Responsavel apenas por armazenar, buscar e remover registros.
 * Os dados existem somente enquanto o programa esta aberto.
 */
public class MovimentacaoRepository {

    private final List<Movimentacao> movimentacoes = new ArrayList<>();
    private int proximoId = 1;

    /** Gera o proximo ID sequencial unico. */
    public int gerarId() {
        return proximoId++;
    }

    public void adicionar(Movimentacao movimentacao) {
        movimentacoes.add(movimentacao);
    }

    /** Retorna uma copia da lista para proteger os dados internos. */
    public List<Movimentacao> listar() {
        return new ArrayList<>(movimentacoes);
    }

    /** Remove a movimentacao com o ID informado. Retorna true se removeu. */
    public boolean removerPorId(int id) {
        return movimentacoes.removeIf(m -> m.getId() == id);
    }
}
