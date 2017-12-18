package com.gps.g13.expensestracker.gestaodedados;

import java.io.Serializable;
import java.util.Date;

public class Transacao implements Serializable {
    private double montante;
    private Date data;
    private String nome;
    private Categoria categoria;

    public Transacao(double montante, Date data, String nome, Categoria categoria) {
        if (montante < 0) {
            montante = 0.0;
        }
        this.montante = montante;
        this.data = data;
        this.nome = nome;
        this.categoria = categoria;
    }

    public double getMontante() {
        return montante;
    }

    public void setMontante(double montante) {
        if (montante > 0) {
            this.montante = montante;
        }
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    @Override
    public int hashCode() {
        return (int) (nome.hashCode() + data.hashCode() + montante);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Transacao) {
            if (object == this) {
                return true;
            }
            Transacao transacao = (Transacao) object;
            if (transacao.getNome().equals(this.getNome()) && transacao.getData().equals(this.getData()) && transacao.getMontante() == (this.getMontante()) && this.getCategoria() == transacao.getCategoria()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Categoria getCategoria() {
        return categoria;
    }
}
