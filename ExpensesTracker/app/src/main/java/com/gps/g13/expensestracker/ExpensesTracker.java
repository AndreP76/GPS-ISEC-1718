package com.gps.g13.expensestracker;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.gps.g13.expensestracker.gestaodedados.GestorDados;

/**
 * Created by andre on 12/8/17.
 */

public class ExpensesTracker extends Application {
    static GestorDados gd;
    static Context c = null;
    public static GestorDados getGestorDadosGlobal(){
        if(gd == null){
            Log.w("[GLOBAL] :: ","Uma atividade pediu o gestor de dados e ele era null. Isto é suposto acontecer só uma vez");
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
