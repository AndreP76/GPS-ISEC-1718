package com.gps.g13.expensestracker;

import com.gps.g13.expensestracker.gestaodedados.CategoriaRendimento;
import com.gps.g13.expensestracker.gestaodedados.Dados;
import com.gps.g13.expensestracker.gestaodedados.GestorDados;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidAmmountException;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidCategoryException;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidDateException;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidNameException;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidTransactionException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class GestorDadosTest {


    @Rule
    public ExpectedException thrown = ExpectedException.none();
    GestorDados gestor;

    @Before
    public void start() {
        gestor = new GestorDados(new Dados());
    }

    @Test
    public void verificaTransacaoAdicionada() throws InvalidDateException, InvalidNameException, InvalidAmmountException, InvalidCategoryException {
        //adiciona uma transacao 'a categoria "Rendimentos". Verifica depois se essa trasacao existe
        gestor.adicionaTransacao("Rendimentos", "testNome", 20.0, new Date());
        assertTrue(gestor.getCategoriaRendimento().containsTransacao("testNome"));

        //adiciona uma transacao 'a categoria "Alojamento", ou seja, uma despesa. Verifica depois se essa trasacao existe
        gestor.adicionaTransacao("Alojamento", "testNome", 20.0, new Date());
        assertTrue(gestor.getCategoriaDespesas("Alojamento").containsTransacao("testNome"));
    }


    @Test
    public void verificaEditacaoTransacao() throws InvalidCategoryException, InvalidTransactionException, InvalidDateException, InvalidNameException, InvalidAmmountException {
        gestor.adicionaTransacao("Rendimentos", "testNome", 20.0, new Date());
        gestor.adicionaTransacao("Alojamento", "testNome", 20.0, new Date());

        //altera o nome de uma transacao, neste caso a transacao anteriormente criada nos Rendimentos
        gestor.editarTransacao("Rendimentos", "testNome", "testNome2", 21.0, new Date());
        //verifica se a alteracao foi feita
        assertTrue(gestor.getTransacoesCategoria("Rendimentos").get(0).getNome().equals("testNome2"));

        //altera o nome de uma transacao, neste caso a transacao anteriormente criada nos Rendimentos
        gestor.editarTransacao("Alojamento", "testNome", "testNome2", 21.0, new Date());
        //verifica se a alteracao foi feita
        assertTrue(gestor.getTransacoesCategoria("Alojamento").get(0).getNome().equals("testNome2"));

    }

    @Test
    public void removeTransacao() throws InvalidDateException, InvalidNameException, InvalidAmmountException, InvalidCategoryException, InvalidTransactionException {
        gestor.adicionaTransacao("Rendimentos", "testNome", 20.0, new Date());
        gestor.adicionaTransacao("Alojamento", "testNome", 20.0, new Date());
        //remove a transacao criada anteriormente nos "Rendimentos". Verifica depois se essa trasacao existe
        gestor.removeTransacao("Rendimentos", "testNome");
        assertFalse((gestor.getCategoriaRendimento().containsTransacao("testNome")));
        //remove  a transacao criada anteriormente na categoria "Alojamento", ou seja, uma despesa. Verifica depois se essa trasacao existe
        gestor.removeTransacao("Alojamento", "testNome");
        assertFalse(gestor.getCategoriaDespesas("Alojamento").containsTransacao("testNome"));
    }

    @Test
    public void verificaValidacoes() throws InvalidDateException, InvalidNameException, InvalidAmmountException, InvalidCategoryException {

        thrown.expect(InvalidCategoryException.class);
        gestor.adicionaTransacao("asdsa", "testNome", 20.0, new Date());

        thrown.expect(InvalidNameException.class);
        gestor.adicionaTransacao("asdsa", "", 20.0, new Date());

        thrown.expect(InvalidAmmountException.class);
        gestor.adicionaTransacao("asdsa", "testeNome", -20.0, new Date());

        thrown.expect(InvalidDateException.class);
        gestor.adicionaTransacao("asdsa", "testeNome", -20.0, new Date(200));

    }
}