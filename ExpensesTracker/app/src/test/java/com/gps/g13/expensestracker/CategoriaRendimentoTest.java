package com.gps.g13.expensestracker;


import com.gps.g13.expensestracker.gestaodedados.CategoriaRendimento;
import com.gps.g13.expensestracker.gestaodedados.Transacao;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class CategoriaRendimentoTest {

    @Test
    public void testCategoriaRendimento() {

        CategoriaRendimento r = new CategoriaRendimento();

        //sendo realizada uma transacao/despesa de 5e....
        r.adicionarTransacao(new Transacao(5.0, new Date(), "teste",null));

        //o resultado da getResumoDeTransacoes e' 5, pq foi adicionado um rendimento de 5e
        assertEquals(5.0, r.getResumoDeTransacoes(), 0);




    }
}
