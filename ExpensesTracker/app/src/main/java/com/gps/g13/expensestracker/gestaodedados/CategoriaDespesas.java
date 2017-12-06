package com.gps.g13.expensestracker.gestaodedados;

import java.util.List;

/**
 * Created by Chamuscado on 06/12/2017.
 */

public class CategoriaDespesas extends Categoria {

    private double orcamento;

    public CategoriaDespesas(String nome) {
        super(nome);
        orcamento = 0;
    }

    public double getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(double orcamento) {
        this.orcamento = orcamento;
    }

    @Override
    public double getResumoDeTransacoes() {
        double soma = 0.0;
        List<Transacao> lista = getListaDeTransacoes();

        if (lista != null && lista.size() > 0) {
            for (int i = 0; i < lista.size(); i++) {
                soma += lista.get(i).getMontante();
            }
        }

        return -soma;
    }

    public double getOrcamentoRestante() {
        return orcamento + getResumoDeTransacoes();
    }
}
