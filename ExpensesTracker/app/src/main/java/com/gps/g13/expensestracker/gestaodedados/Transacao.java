package com.gps.g13.expensestracker.gestaodedados;

import java.io.Serializable;
import java.util.Date;

public class Transacao implements Serializable {
    private Double montante;
    private Date data;
    private String nome;

    public Transacao(Double montante, Date data, String nome) {
        if (montante < 0) {
            montante = 0.0;
        }
        this.montante = montante;
        this.data = data;
        this.nome = nome;
    }

    public Double getMontante() {
        return montante;
    }

    public void setMontante(Double montante) {
        if (montante > 0)
            this.montante = montante;
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
}
