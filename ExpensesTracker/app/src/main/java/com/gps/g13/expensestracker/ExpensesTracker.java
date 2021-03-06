package com.gps.g13.expensestracker;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.gps.g13.expensestracker.gestaodedados.GestorDados;

/**
 * Created by andre on 12/8/17.
 */

//DONE : O calculo do orçamento restante esta mal, devolve sempre negativo
//DONE : A vista das categorias nunca atualiza
//DONE : As datas aparecem com o mês anterior
//DONE : Datas na categoria rendimentos mal formatadas
//DONE2 : Rever formatações de todas as strings

public class ExpensesTracker extends Application {
    static GestorDados gd;
    static Context c = null;

    public static GestorDados getGestorDadosGlobal(Context ctx) {
        if (c == null) {
            c = ctx;
        }
        if (gd == null) {
            Log.w("[GLOBAL] :: ", "Uma atividade pediu o gestor de dados e ele era null. Isto é suposto acontecer só uma vez");
            gd = new GestorDados(c);
        }
        return gd;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ExpensesTracker.c = this;
    }
}
