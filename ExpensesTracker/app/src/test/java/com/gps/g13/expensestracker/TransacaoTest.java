package com.gps.g13.expensestracker;

import com.gps.g13.expensestracker.gestaodedados.Transacao;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TransacaoTest {

    @Test
    public void testeMontante() {
        Transacao t = new Transacao(-1.0, new Date(), "teste1", null);

        //quando se cria uma transacao o seu montante tem de ser 0
        assertEquals(0.0,t.getMontante(),0);

        //quando se introduz um montante negativo o seu montante não se deve alterar
        t.setMontante(-9.0);
        assertEquals(0.0,t.getMontante(),0);

        //quando se introduz x montante positivo o seu montante tem de ser x
        t.setMontante(100.0);
        assertEquals(100.0,t.getMontante(),0);

        t = new Transacao(100.0, new Date(), "teste1", null);
        assertEquals(100.0, t.getMontante(), 0);

        //quando se introduz um montante negativo o seu montante não se deve alterar
        t.setMontante(-9.0);
        assertEquals(100.0, t.getMontante(), 0);

        //quando se introduz x montante positivo o seu montante tem de ser x
        t.setMontante(90.0);
        assertEquals(90.0, t.getMontante(), 0);

    }
}