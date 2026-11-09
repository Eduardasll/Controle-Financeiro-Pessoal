package br.com.portfolio.financeiro.model;

import java.math.BigDecimal;

/**
 * Representa um registro financeiro, que pode ser uma receita ou uma despesa.
 * Os atributos sao privados (encapsulamento) e so podem ser lidos pelos getters.
 */
public class Movimentacao {

    private final int id;
    private final String descricao;
    private final BigDecimal valor;
    private final TipoMovimentacao tipo;
    private final String categoria;

    public Movimentacao(int id, String descricao, BigDecimal valor,
                        TipoMovimentacao tipo, String categoria) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public TipoMovimentacao getTipo() {
        return tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return String.format("ID %d | %-8s | %-15s | %-15s | R$ %s",
                id, tipo, descricao, categoria, valor);
    }
}
