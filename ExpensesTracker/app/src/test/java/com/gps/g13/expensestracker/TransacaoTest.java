package com.gps.g13.expensestracker;

import com.gps.g13.expensestracker.gestaodedados.Dados;
import com.gps.g13.expensestracker.gestaodedados.GestorDados;
import com.gps.g13.expensestracker.gestaodedados.Transacao;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransacaoTest {


    @Rule
    public ExpectedException thrown = ExpectedException.none();
    Transacao t;

    @Before
    public void start() {
        t = new Transacao(-1.0, new Date(), "teste1", null);
    }

    @Test
    public void verificaValorInicial(){
        //quando se cria uma transacao o seu montante tem de ser 0
        assertEquals(0.0, t.getMontante(), 0);
    }

    @Test
    public void verificaValoresNegativos(){
        //quando se introduz um montante negativo o seu montante não se deve alterar
        t.setMontante(-9.0);
        assertEquals(0.0, t.getMontante(), 0);
    }

    @Test
    public void verificaValoresPositivos(){
        //quando se introduz x montante positivo o seu montante tem de ser x
        t.setMontante(100.0);
        assertEquals(100.0, t.getMontante(), 0);
    }

    @Test
    public void verificaEquals(){

        Transacao t2 = new Transacao(-1.0, new Date(), "teste1", null);
        assertTrue(t.equals(t2));
    }

}
