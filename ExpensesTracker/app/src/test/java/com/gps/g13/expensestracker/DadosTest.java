package com.gps.g13.expensestracker;

import com.gps.g13.expensestracker.gestaodedados.Dados;
import com.gps.g13.expensestracker.gestaodedados.ListaNomesCategoriasDespesas;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidCategoryException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Date;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DadosTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    Dados dados;
    ListaNomesCategoriasDespesas[] nomes;

    @Before
    public void start() {
        dados = new Dados();
        nomes = ListaNomesCategoriasDespesas.values();
    }

    @Test
    public void verificarNumeroDeCategorias() {
        //Devem exitir 7 categorias, 6 desperas, 1 rendimentos
        assertEquals(dados.getCategorias().size(), ListaNomesCategoriasDespesas.values().length + 1);

    }

    @Test
    public void verificarSeTemTodasAsCategorias() {

        for (ListaNomesCategoriasDespesas i : nomes) {
            assertTrue(dados.containsCategory(i.name()));
        }
        assertTrue(dados.containsCategory("Rendimentos"));
        assertFalse(dados.containsCategory("fopierjkferlsmgt"));

    }

    @Test
    public void verificarSeAdicionaTransacaoERemove() {

        dados.adicionaTransacao("Rendimentos", "trasacao1", 100, new Date());

        assertEquals(dados.getTransacoesCategoria("Rendimentos").size(), 1);

        dados.adicionaTransacao("Rendimentos", "trasacao2", 100, new Date());
        assertEquals(dados.getTransacoesCategoria("Rendimentos").size(), 2);

        dados.removeTransacao("Rendimentos", "trasacao1");
        assertEquals(dados.getTransacoesCategoria("Rendimentos").size(), 1);

        dados.removeTransacao("Rendimentos", "trasacao1");
        assertEquals(dados.getTransacoesCategoria("Rendimentos").size(), 1);
    }

    @Test
    public void verificarSeEditaTransacao() {
        Date date = new Date();
        dados.adicionaTransacao("Rendimentos", "trasacao2", 100.0, date);

        assertEquals(dados.getTransacoesCategoria("Rendimentos").get(0).getNome(), "trasacao2");

        dados.editaTransacao("Rendimentos", "trasacao2", "Mesada", 200.0, date);

        assertEquals(dados.getTransacoesCategoria("Rendimentos").get(0).getNome(), "Mesada");
        assertEquals(dados.getTransacoesCategoria("Rendimentos").get(0).getMontante(), 200.0, 0);
    }

    @Test
    public void VerificagetCategoria() throws InvalidCategoryException {
        Random rand = new Random();
        int i = rand.nextInt(nomes.length);
        assertEquals(dados.getCategoria(nomes[i].name()).getNome(), nomes[i].name());

        thrown.expect(InvalidCategoryException.class);
        dados.getCategoria("weferfwere");
    }


}
