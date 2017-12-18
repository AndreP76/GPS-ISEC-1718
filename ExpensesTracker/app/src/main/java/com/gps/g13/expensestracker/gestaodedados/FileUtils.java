package com.gps.g13.expensestracker.gestaodedados;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

//Visto que havia funcionalidades na gestao de ficheiros que so estavam disponiveis no API 26 tivemos que criar codigo
public final class FileUtils {
    //copia um ficheiro de uma origem (origin) para um destino (destination) - caso ja exista um ficheiro no destino com o mesmo nome é apagado e escrito o novo
    public static void copyFiles(String origin, String destination) {
        if (fileExists(origin)) {
            try {
                int i = 0;
                for (i = 0; i < 10; ++i) {
                    Log.v("[GESTOR] :: ", "Tentativa " + (i + 1));
                    if (removeFile(origin)) {
                        break;
                    }
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
            FileOutputStream fOS = new FileOutputStream(destination);
            BufferedReader brfIS = new BufferedReader(new InputStreamReader(fIS));
            BufferedWriter bwfOS = new BufferedWriter(new OutputStreamWriter(fOS));
            String line;
            try {
                while ((line = brfIS.readLine()) != null) {
                    bwfOS.write(line, 0, line.length());
                }

                brfIS.close();
                bwfOS.close();
                fIS.close();
                fOS.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Metodo que remove um ficheiro no caminho recebido por parametro
    private static boolean removeFile(String origin) throws FileNotFoundException {
        if (fileExists(origin)) {
            File file = new File(origin);
            return file.delete();
        } else {
            throw new FileNotFoundException();
        }
    }

    //Metodo que verifica a existencia de um ficheiro no caminho recebido por parametro
    private static boolean fileExists(String origin) {
        File file = new File(origin);
        return file.exists();
    }
}
