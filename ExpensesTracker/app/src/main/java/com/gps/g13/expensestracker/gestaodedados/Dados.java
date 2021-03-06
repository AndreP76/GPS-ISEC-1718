package com.gps.g13.expensestracker.gestaodedados;

import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidCategoryException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Dados implements Serializable {
    public static final String RENDIMENTOS_KEY = "Rendimentos";
    private Map<String, Categoria> categorias;
    // as validações do gestor de dados sao criticas ao bom funcionamento desta classe

    public Dados() {
        categorias = new HashMap<>();
        ListaNomesCategoriasDespesas[] nomes = ListaNomesCategoriasDespesas.values();
        for (ListaNomesCategoriasDespesas lista : nomes) {
            categorias.put(lista.name(), new CategoriaDespesas(lista.name()));
        }
        categorias.put(RENDIMENTOS_KEY, new CategoriaRendimento());
    }

    public boolean containsCategory(String name) {
        for (Categoria categoria : getCategorias()) {
            if (categoria.getNome().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public List<Categoria> getCategorias() {
        return new ArrayList<>(categorias.values());
    }

    public List<Transacao> getTransacoesCategoria(String nome) {
        Categoria cat = categorias.get(nome);
        return cat != null ? cat.getListaDeTransacoes() : null;
    }

    public boolean adicionaTransacao(String categoria, String nome, double montante, Date data) {
        boolean resposta = false;
        Categoria categoria2 = categorias.get(categoria);
        if (categoria2 != null) {
            categoria2.adicionarTransacao(new Transacao(montante, data, nome, categoria2));
            resposta = true;
        }
        return resposta;
    }

    public boolean removeTransacao(String categoria, String nome) {
        boolean resposta = false;
        Categoria categoria2 = categorias.get(categoria);
        if (categoria2 != null) {
            List<Transacao> l = categoria2.getListaDeTransacoes();
            for (Transacao i : l) {
                if (i.getNome().compareTo(nome) == 0) {
                    categoria2.removeTransacao(i);
                    break;
                }
            }
            resposta = true;
        }
        return resposta;
    }

    public boolean editaTransacao(String categoria, String nome, String nomeNovo, double montante, Date data) {
        boolean resposta = false;
        Categoria categoria2 = categorias.get(categoria);
        if (categoria2 != null) {
            List<Transacao> list = categoria2.getListaDeTransacoes();
            for (Transacao transacao : list) {
                if (transacao.getNome().compareTo(nome) == 0) {
                    transacao.setMontante(montante);
                    transacao.setData(data);
                    transacao.setNome(nomeNovo);
                    break;
                }
            }
            resposta = true;
        }
        return resposta;
    }

    //retorna uma categoria se ela existir
    //pode retornar null ou atirar exceçoes
    public Categoria getCategoria(String categoria) throws InvalidCategoryException {
        if (containsCategory(categoria)) {
            return categorias.get(categoria);
        } else {
            throw new InvalidCategoryException("Category " + categoria + " is invalid");
        }
    }
}