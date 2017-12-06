package com.gps.g13.expensestracker;

import com.gps.g13.expensestracker.gestaodedados.Transacao;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TransacaoTest {

    @Test
    public void testeMontate() {
        Transacao t = new Transacao(-1.0, new Date(), "teste1");
        assertEquals(0.0,t.getMontante(),0);
        t.setMontante(-9.0);
        assertEquals(0.0,t.getMontante(),0);
        t.setMontante(100.0);
        assertEquals(100.0,t.getMontante(),0);
    }
}
