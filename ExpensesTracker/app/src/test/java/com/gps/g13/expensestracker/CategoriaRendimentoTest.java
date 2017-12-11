package com.gps.g13.expensestracker;


import com.gps.g13.expensestracker.gestaodedados.CategoriaDespesas;
import com.gps.g13.expensestracker.gestaodedados.CategoriaRendimento;
import com.gps.g13.expensestracker.gestaodedados.Transacao;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

import static com.gps.g13.expensestracker.gestaodedados.CategoriaRendimento.NOME_RENDIMENTO;
import static org.junit.Assert.assertEquals;

public class CategoriaRendimentoTest {


    @Rule
    CategoriaRendimento r;

    @Before
    public void start() {
        r = new CategoriaRendimento();
    }

    @Test
    public void verificaNomeCategoria()
    {
        assertEquals(NOME_RENDIMENTO, r.getNome(), 0);

    }
    @Test
    public  void verificaGetResumoTransacoes(){
        //sendo realizada uma transacao/despesa de 5e....
        r.adicionarTransacao(new Transacao(5.0, new Date(), "teste", null));

        //o resultado da getResumoDeTransacoes e' 5, pq foi adicionado um rendimento de 5e
        assertEquals(5.0, r.getResumoDeTransacoes(), 0);
    }
}
