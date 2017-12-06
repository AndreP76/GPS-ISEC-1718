package com.gps.g13.expensestracker.gestaodedados;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Dados implements Serializable {
    private Map<String, Categoria> categorias;

    public Dados() {
        categorias = new HashMap<>();
        ListaNomesCategoriasDespesas[] nomes = ListaNomesCategoriasDespesas.values();
        for (ListaNomesCategoriasDespesas i : nomes) {
            categorias.put(i.name(), new CategoriaDespesas(i.name()));
        }
        categorias.put("Rendimentos", new CategoriaRendimento());
    }

    public Map getCategorias() {
        return categorias;
    }

    public List getTransacoesCategoria(String nome) {
        Categoria cat = categorias.get(nome);
        return cat != null ? cat.getListaDeTransacoes() : null;
    }

    public boolean adicionaTransacao(String categoria, String nome, double montante, Date data) {
        return;
    }

    public boolean removeTransacao(String categoria, String nome) {
        return true;
    }

    public boolean editaTransacao(String categoria, String nome, double montante, Date data) {
        return true;
    }


}