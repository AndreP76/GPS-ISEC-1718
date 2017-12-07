package com.gps.g13.expensestracker.gestaodedados;

import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by Chamuscado on 06/12/2017.
 */

public class GestorDados {
    private static final String BACKUP_PATH = "";
    private Dados data;

    GestorDados(String dataFilePath) {
        this.data = readDataFromFile(dataFilePath);
    }

    GestorDados(Dados data) {
        this.data = data;
    }

    private Dados readDataFromBackupFile(String originFile) {
        try (FileInputStream bIn = new FileInputStream(BACKUP_PATH)) {
            try (ObjectInputStream OIS = new ObjectInputStream(bIn)) {
                Object o = OIS.readObject();
                if (!(o instanceof Dados)) {
                    throw new ClassNotFoundException();//error reading from backup. Create empty object
                } else {
                    overrideFileWithBackup(originFile);
                    return (Dados) o;
                }
            } catch (ClassNotFoundException e1) {
                return new Dados();
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Log.w("[GESTOR] :: ", "readDataFromBackup retornou null");
        return null;
    }

    private Dados readDataFromFile(String dataFile) {
        try (FileInputStream fIn = new FileInputStream(dataFile)) {
            try (ObjectInputStream OIS = new ObjectInputStream(fIn)) {
                Object o = OIS.readObject();
                if (!(o instanceof Dados)) {
                    throw new ClassNotFoundException();// so we can read from the backup in the catch clause
                } else {
                    return (Dados) o;
                }
            } catch (ClassNotFoundException e) {
                return readDataFromBackupFile(dataFile);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.w("[GESTOR] :: ", "readDataFromFile retornou null");
        return null;
    }

    private void overrideFileWithBackup(String dataFile) {
        FileUtils.copyFiles(BACKUP_PATH, dataFile);
    }

    private void writeDataToFile(URL dataFile) {
        try {
            try (FileOutputStream writeOut = new FileOutputStream(dataFile.getFile())) {
                try (ObjectOutputStream oos = new ObjectOutputStream(writeOut)) {
                    oos.writeObject(data);
                    oos.flush();
                    writeOut.flush();
                    writeOut.close();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Categoria> getCategorias() {
        return data.getCategorias();
    }

    public List<Transacao> getTransacoesCategoria(String name) {
        return data.getTransacoesCategoria(name);
    }

}
