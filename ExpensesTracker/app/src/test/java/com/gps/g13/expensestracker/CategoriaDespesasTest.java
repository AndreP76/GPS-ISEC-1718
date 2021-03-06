package com.gps.g13.expensestracker;

import com.gps.g13.expensestracker.gestaodedados.CategoriaDespesas;
import com.gps.g13.expensestracker.gestaodedados.Transacao;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class CategoriaDespesasTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    CategoriaDespesas d;

    @Before
    public void start() {
        d = new CategoriaDespesas("teste");
    }

    @Test
    public void verificaDespesaBemCriada(){
        //quando se cria uma despesa o seu orcamento e' 0
        assertEquals(0, d.getOrcamento(), 0);
    }

    @Test
    public void verificaAlteracaoOrcamento(){
        //quando se introduz x orcamento positivo
        d.setOrcamento(50.3);
        assertEquals(50.3, d.getOrcamento(), 0);

        d.setOrcamento(-123);
        assertEquals(50.3, d.getOrcamento(), 0);

    }

    @Test
    public void verificaAdicaoTransacoes(){
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
    }


    @Test
    public void verificaResumoTransacoes(){
        //admitindo que o orcamento e' 20e...
        d.setOrcamento(30);

        Transacao teste1 = new Transacao(5.0, new Date(), "teste", null);
        Transacao teste2 = new Transacao(15.0, new Date(), "teste", null);
        d.adicionarTransacao(teste1);
        d.adicionarTransacao(teste2);

        assertEquals(20.0, d.getResumoDeTransacoes(), 0);

    }

    @Test
    public void verificaOrcamentoRestante(){
        //admitindo que o orcamento e' 20e...
        d.setOrcamento(30);

        Transacao teste1 = new Transacao(5.0, new Date(), "teste", null);
        Transacao teste2 = new Transacao(15.0, new Date(), "teste", null);
        d.adicionarTransacao(teste1);
        d.adicionarTransacao(teste2);
        //o resultado tera de ser 30e-20e = 10e
        assertEquals(10, d.getOrcamentoRestante(), 0);
    }
}
