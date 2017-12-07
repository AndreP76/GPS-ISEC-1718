package com.gps.g13.expensestracker.gestaodedados;

import java.util.List;

public class CategoriaDespesas extends Categoria {

    private double orcamento;

    public CategoriaDespesas(String nome) {
        super(nome);
        orcamento = 0;
    }

    public double getOrcamento() {
        return orcamento;
    }

    //so aceita orcamentos de valor positivo
    public void setOrcamento(double orcamento) {
        if (orcamento > 0) {
            this.orcamento = orcamento;
        }
    }

    //este metodo percorre todas as transacoes realizadas e adiciona o seu montante. Neste caso como as transacoes sao Despesas retornamos como um valor negativo
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

    //vai retornar o orcamento que ainda resta da respetiva Despesa
    public double getOrcamentoRestante() {
        return orcamento + getResumoDeTransacoes();
    }
}
