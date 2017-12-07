package com.gps.g13.expensestracker;

import com.gps.g13.expensestracker.gestaodedados.Dados;
import com.gps.g13.expensestracker.gestaodedados.GestorDados;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidAmmountException;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidCategoryException;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidDateException;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidNameException;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidTransactionException;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class GestorDadosTest {
    GestorDados gestor;

    @Before
    public void instanciaObjetos(){
        gestor = new GestorDados(new Dados());



        //gestor.guardaDados();
    }

    @Test
    public void transacoes() throws InvalidDateException, InvalidNameException, InvalidAmmountException, InvalidCategoryException, InvalidTransactionException {

        //adiciona uma transacao 'a categoria "Rendimentos". Verifica depois se essa trasacao existe
        gestor.adicionaTransacao("Rendimentos", "testNome", 20.0, new Date());
        assertTrue(gestor.getCategoriaRendimento().containsTransacao("testNome"));

        //adiciona uma transacao 'a categoria "Alojamento", ou seja, uma despesa. Verifica depois se essa trasacao existe
        gestor.adicionaTransacao("Alojamento", "testNome", 20.0, new Date());
        assertTrue(gestor.getCategoriaDespesas("Alojamento").containsTransacao("testNome"));


        //altera o nome de uma transacao, neste caso a transacao anteriormente criada nos Rendimentos
        gestor.editarTransacao("Rendimentos", "testNome", "testNome2", 21.0, new Date());


        //altera o nome de uma transacao, neste caso a transacao anteriormente criada nos Rendimentos
        gestor.editarTransacao("Alojamento", "testNome", "testNome2", 21.0, new Date());

        //verifica se a alteracao foi feita
        assertTrue(gestor.getTransacoesCategoria("Rendimentos").get(0).getNome().equals("testNome2"));

        //verifica se a alteracao foi feita
        assertTrue(gestor.getTransacoesCategoria("Alojamento").get(0).getNome().equals("testNome2"));

        //remove a transacao criada anteriormente nos "Rendimentos". Verifica depois se essa trasacao existe
        gestor.removeTransacao("Rendimentos", "testNome2");
        assertFalse((gestor.getCategoriaRendimento().containsTransacao("testNome")));

        //remove  a transacao criada anteriormente na categoria "Alojamento", ou seja, uma despesa. Verifica depois se essa trasacao existe
        gestor.removeTransacao("Alojamento", "testNome2");
        assertFalse(gestor.getCategoriaDespesas("Alojamento").containsTransacao("testNome"));

    }

    @Test
    public void ficheiros() throws InvalidDateException, InvalidNameException, InvalidAmmountException, InvalidCategoryException {

        //adiciona uma transacao 'a categoria "Rendimentos"
        //gestor.adicionaTransacao("Rendimentos", "testNome", 20.0, new Date());

        //gestor.guardaDados();

        //GestorDados gdAux = new GestorDados(new Dados());

        //gdAux.carregaDados();

        //verifica se o ficheiro lido tem a transacao adicionada nos rendimentos
        //assertTrue(gdAux.getCategoriaRendimento().containsTransacao("testNome"));
    }
}