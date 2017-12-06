package com.gps.g13.expensestracker.gestaodedados;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Categoria implements Serializable {
    private String nome;
    private List<Transacao> transacoes;

    public Categoria(String nome){
        this.nome = nome;
        transacoes = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void adicionarTransacao(Transacao transacao) {
        if (transacao != null && transacao.getMontante() > 0) {
            transacoes.add(transacao);
        }
    }

    public void removeTransacao(Transacao transacao) {
        if (transacao != null) {
            transacoes.remove(transacao);
        }
    }

    public abstract double getResumoDeTransacoes();

    public List<Transacao> getListaDeTransacoes() {
        return transacoes;
    }

    public Transacao getTransacao(int index) {
        if (index < 0 || index >= transacoes.size()) {
            return null;
        } else {
            return transacoes.get(index);
        }
    }
}
