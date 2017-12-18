package com.gps.g13.expensestracker.gestaodedados;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class Categoria implements Serializable {
    private String nome;
    private List<Transacao> transacoes;

    public Categoria(String nome) {
        this.nome = nome;
        transacoes = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    //quando se adiciona uma transacao só é possivel adicionar transacoes nao nulas e montante positivo
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

    public abstract double getResumoDeTransacoes();// as classes derivadas implementam isto de forma apropriada

    public List<Transacao> getListaDeTransacoes() {
        Collections.sort(transacoes, new Comparator<Transacao>() {
            @Override
            public int compare(Transacao transacao1, Transacao transacao2) {
                return transacao2.getData().compareTo(transacao1.getData());
            }
        });
        return transacoes;
    }

    //se o index que é recebido por parametro é menor que 0 ou maior que o tamanho da List é retornado null
    public Transacao getTransacao(int index) {
        if (index < 0 || index >= transacoes.size()) {
            return null;
        } else {
            return transacoes.get(index);
        }
    }

    public boolean containsTransacao(String nome) {
        for (Transacao transacao : transacoes) {
            if (transacao.getNome().equals(nome)) {
                return true;
            }
        }
        return false;
    }
}
