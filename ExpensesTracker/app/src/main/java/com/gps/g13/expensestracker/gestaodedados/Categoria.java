package com.gps.g13.expensestracker.gestaodedados;

import java.io.Serializable;
import java.util.List;

public abstract class Categoria implements Serializable {
    private String nome;
    private List<Transacao> transacoes;

    public String getNome() {
        return nome;
    }

    public void adicionarTransacao(Transacao transacao){

    }
}
