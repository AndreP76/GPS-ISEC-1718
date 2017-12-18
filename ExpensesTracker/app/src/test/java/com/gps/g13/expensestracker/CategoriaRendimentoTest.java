package com.gps.g13.expensestracker;


import com.gps.g13.expensestracker.gestaodedados.CategoriaDespesas;
import com.gps.g13.expensestracker.gestaodedados.CategoriaRendimento;
import com.gps.g13.expensestracker.gestaodedados.Transacao;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static com.gps.g13.expensestracker.gestaodedados.CategoriaRendimento.NOME_RENDIMENTO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CategoriaRendimentoTest {


    @Rule
    public ExpectedException thrown = ExpectedException.none();
    CategoriaRendimento r;

    @Before
    public void start() {
        r = new CategoriaRendimento();
    }

    @Test
    public void verificaNomeCategoria()
    {
        assertEquals(NOME_RENDIMENTO, r.getNome());
    }

    @Test
    public void verificaAdicaoTransacoes(){
        //sendo realizada uma transacao/rendimento de 5e....
        Transacao teste1 = new Transacao(5.0, new Date(), "teste", null);
        //sendo realizada uma transacao/rendimento de -14e....
        Transacao teste2 = new Transacao(-14.0, new Date(), "teste", null);
        r.adicionarTransacao(teste1);
        r.adicionarTransacao(teste2);
        //a transacao tem de estar registada...
        assertTrue(r.getListaDeTransacoes().contains(teste1));
        //a transacao nao pode estar registada...
        assertFalse(r.getListaDeTransacoes().contains(teste2));
    }

    @Test
    public  void verificaGetResumoTransacoes(){
        //sendo realizada uma transacao/rendimento de 5e....
        r.adicionarTransacao(new Transacao(5.0, new Date(), "teste", null));

        //o resultado da getResumoDeTransacoes e' 5, pq foi adicionado um rendimento de 5e
        assertEquals(5.0, r.getResumoDeTransacoes(), 0);
    }
}
