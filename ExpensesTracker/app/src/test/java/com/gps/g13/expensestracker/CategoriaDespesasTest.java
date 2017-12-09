package com.gps.g13.expensestracker;

import com.gps.g13.expensestracker.gestaodedados.CategoriaDespesas;
import com.gps.g13.expensestracker.gestaodedados.Transacao;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class CategoriaDespesasTest {


    @Test
    public void testCategoriaDespesas() {

        CategoriaDespesas d = new CategoriaDespesas("teste");

        //quando se cria uma despesa o seu orcamento e' 0
        assertEquals(0, d.getOrcamento(), 0);

        //quando se introduz x orgamento positivo
        d.setOrcamento(50.3);
        assertEquals(50.3, d.getOrcamento(), 0);

        //quando se introduz x orgamento negativo
        d.setOrcamento(-49.0);
        //assertEquals(0, d.getOrcamento(),0); //POR IMPLEMENTAR! QUANDO SE POE UM ORCAMENTO NEGATIVO TERA DE SER 0

        /*
            E IMPLEMENTAR CASO RECEBA LETRAS OU CENAS DO GENERO??

         */

        //admitindo que o orcamento e' 20e...
        d.setOrcamento(20);

        //sendo realizada uma transacao/despesa de 5e....
        Transacao teste1 = new Transacao(5.0, new Date(), "teste", null);

        //sendo realizada uma transacao/despesa de -14e....
        Transacao teste2 = new Transacao(-14.0, new Date(), "teste", null);

        d.adicionarTransacao(teste1);
        d.adicionarTransacao(teste2);
        //a transacao tem de estar registada...
        assertTrue(d.getListaDeTransacoes().contains(teste1));
        //a transacao nao pode estar registada...
        assertFalse(d.getListaDeTransacoes().contains(teste2));


        //tendo sido adicionada uma despesa de 5e anteriormente...
        //o resultado da getResumoDeTransacoes e' -5, pq foi realizada a despesa de 5e
        assertEquals(-5.0, d.getResumoDeTransacoes(), 0);

        //o resultado tera de ser 20e-5e = 15e
        assertEquals(15, d.getOrcamentoRestante(), 0);
    }

}
