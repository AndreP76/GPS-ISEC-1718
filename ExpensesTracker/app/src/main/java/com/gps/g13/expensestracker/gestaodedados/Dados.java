package com.gps.g13.expensestracker.gestaodedados;

import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidCategoryException;

import java.io.Serializable;
import java.util.ArrayList;
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

    public boolean containsCategory(String name) {
        for (Categoria c :
                getCategorias()) {
            if (c.getNome().equals(name)) {
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
        Categoria c = categorias.get(categoria);
        if (c != null) {
            c.adicionarTransacao(new Transacao(montante, data, nome));
            resposta = true;
        }
        return resposta;
    }

    public boolean removeTransacao(String categoria, String nome) {   // TODO -> pode correr extremamanete mal, fica a dica
        boolean resposta = false;
        Categoria c = categorias.get(categoria);
        if (c != null) {
            List<Transacao> l = c.getListaDeTransacoes();
            for (Transacao i : l) {
                if (i.getNome().compareTo(nome) == 0) {
                    c.removeTransacao(i);
                    break;
                }
            }
            resposta = true;
        }
        return resposta;
    }

    public boolean editaTransacao(String categoria, String nome, String nomeNovo, double montante, Date data) {
        boolean resposta = false;
        Categoria c = categorias.get(categoria);
        if (c != null) {
            List<Transacao> l = c.getListaDeTransacoes();
            for (Transacao i : l) {
                if (i.getNome().compareTo(nome) == 0) {
                    i.setMontante(montante);
                    i.setData(data);
                    i.setNome(nomeNovo);
                    break;
                }
            }
            resposta = true;
        }
        return resposta;
    }


    public Categoria getCategoria(String categoria) throws InvalidCategoryException {
        if (containsCategory(categoria)) {
            return categorias.get(categoria);
        } else throw new InvalidCategoryException("Category " + categoria + " is invalid");
    }
}