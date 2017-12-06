package com.gps.g13.expensestracker.gestaodedados;

import java.util.List;

/**
 * Created by Chamuscado on 06/12/2017.
 */

public class CategoriaRendimento extends Categoria {
    public static final String NOME_RENDIMENTO = "Rendimentos";

    public CategoriaRendimento(){
        super(NOME_RENDIMENTO);
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

        return soma;
    }
}
