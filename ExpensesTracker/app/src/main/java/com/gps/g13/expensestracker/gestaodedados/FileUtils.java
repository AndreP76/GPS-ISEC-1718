package com.gps.g13.expensestracker.gestaodedados;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

//Visto que havia funcionalidades na gestao de ficheiros que so estavam disponiveis no API 26 tivemos que criar codigo
public final class FileUtils {


    //copia um ficheiro de uma origem (origin) para um destino (destination) - caso ja exista um ficheiro no destino com o mesmo nome e' apagado e escrito o novo
    public static void copyFiles(String origin, String destination) {
        if (fileExists(origin)) {
            try {
                int i = 0;
                for (i = 0; i < 10; ++i) {
                    Log.v("[GESTOR] :: ", "Tentativa " + (i + 1));
                    if (removeFile(origin)) break;
                    Log.w("[GESTOR] :: ", "Remoção falhou");
                }
                if (i >= 10) {
                    Log.e("[GESTOR] :: ", "Erro ao remover ficheiro " + origin);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        try {
            FileInputStream fIS = new FileInputStream(origin);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Metodo que remove um ficheiro no caminho recebido por parametro
    private static boolean removeFile(String origin) throws FileNotFoundException {
        if (fileExists(origin)) {
            File f = new File(origin);
            return f.delete();
        } else throw new FileNotFoundException();
    }
    //Metodo que verifica a existencia de um ficheiro no caminho recebido por parametro
    private static boolean fileExists(String origin) {
        File f = new File(origin);
        return f.exists();
    }
}
