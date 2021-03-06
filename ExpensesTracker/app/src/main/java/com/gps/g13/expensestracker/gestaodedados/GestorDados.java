package com.gps.g13.expensestracker.gestaodedados;

import android.content.Context;
import android.util.Log;

import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidAmmountException;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidCategoryException;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidDateException;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidNameException;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidTransactionException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class GestorDados implements Serializable {
    private String BACKUP_PATH = "";
    private String STANDARD_PATH = "";
    private Dados data;

    public GestorDados(Dados data) {
        this.data = data;
    } // para testes

    public GestorDados(Context context) {
        BACKUP_PATH = context.getFilesDir().toString() + "/settings.xml";
        STANDARD_PATH = context.getFilesDir().toString() + "/data.bin";
        this.data = readDataFromFile(STANDARD_PATH, true);
        if (this.data == null) {
            this.data = new Dados();
            Log.e("[GESTOR] :: ", "data ficou null depois de ler de ficheiro");
        }
    }

    /**
     * Metodos privados inicio
     **/
    private Dados readDataFromBackupFile(String originFile) {
        try (FileInputStream bIn = new FileInputStream(BACKUP_PATH)) {
            try (ObjectInputStream OIS = new ObjectInputStream(bIn)) {
                Object object = OIS.readObject();
                if (!(object instanceof Dados)) {
                    throw new ClassNotFoundException();//error reading from backup. Create empty object
                } else {
                    overrideFileWithBackup(originFile);
                    return (Dados) object;
                }
            } catch (ClassNotFoundException e1) {
                return new Dados();
            }
        } catch (FileNotFoundException e1) {
            return new Dados();
        } catch (IOException e1) {
            return new Dados();
        }
    }
    private Dados readDataFromFile(String dataFile, boolean useBackup) {
        try (FileInputStream fIn = new FileInputStream(dataFile)) {
            try (ObjectInputStream OIS = new ObjectInputStream(fIn)) {
                Object object = OIS.readObject();
                if (!(object instanceof Dados)) {
                    throw new ClassNotFoundException();// so we can read from the backup in the catch clause
                } else {
                    return (Dados) object;
                }
            } catch (ClassNotFoundException e) {
                if (useBackup)
                    return readDataFromBackupFile(dataFile);
                else return new Dados();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return readDataFromBackupFile(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
            return readDataFromBackupFile(dataFile);
        }
    }
    private void overrideFileWithBackup(String dataFile) {
        FileUtils.copyFiles(BACKUP_PATH, dataFile);
    }
    private void writeDataToFile(String dataFile) {             //TODO -> corrigir no diagrama de class, está na mesma linha - corrigido
        try {
            try (FileOutputStream writeOut = new FileOutputStream(dataFile)) {
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

    /**
     * Metodos privados fim
     **/


    public CategoriaRendimento getCategoriaRendimento() {
        try {
            return (CategoriaRendimento) data.getCategoria(Dados.RENDIMENTOS_KEY);
        } catch (InvalidCategoryException e) {
            Log.wtf("[GESTOR] :: ", "get CategoriaRendimentos was declared invalid!", e);
            return null;
        }
    }
    public Categoria getCategoriaDespesas(String name) throws InvalidCategoryException {
        if (ValidationModule.isValidExpensesCategory(name, data)) {
            return data.getCategoria(name);
        } else {
            throw new InvalidCategoryException("Category " + name + " is not a valid expenses category");
        }
    }
    public List<Categoria> getCategorias() {
        return data.getCategorias();
    }
    public List<Transacao> getTransacoesCategoria(String name) {
        return data.getTransacoesCategoria(name);
    }

    //metodo publico para forçar uma atualização de dados
    public Dados carregaDados() {
        return readDataFromFile(STANDARD_PATH, true);
    }
    //Guarda toda a informacao da classe em um ficheiro
    public void guardaDados() {
        writeDataToFile(STANDARD_PATH);
        Dados dados = readDataFromFile(STANDARD_PATH, false);
        if (dados != null/* && d.equals(data)*/) {
            Log.v("[GESTOR] :: ", "Data integrity confirmed. Replacing backup file");
            writeDataToFile(BACKUP_PATH);
        } else {
            if (dados == null) {
                Log.e("[GESTOR] :: ", "Reading from just written data returned null. Overriding with backup file");
                overrideFileWithBackup(STANDARD_PATH);
            }
        }
    }

    public boolean adicionaTransacao(String categoria, String nome, double montante, Date data) throws InvalidNameException, InvalidAmmountException, InvalidDateException, InvalidCategoryException {
        if (ValidationModule.isValidCategory(categoria, this.data)) {
            if (ValidationModule.isValidName(nome)) {
                if (ValidationModule.isValidAmmount(montante)) {
                    if (ValidationModule.isValidDate(data)) {
                        return this.data.adicionaTransacao(categoria, nome, montante, data);
                    } else {
                        throw new InvalidDateException("Data " + data + "is invalid!");
                    }
                } else {
                    throw new InvalidAmmountException("Ammount " + montante + "is invalid!");
                }
            } else {
                throw new InvalidNameException("Name " + nome + "is invalid!");
            }
        } else {
            throw new InvalidCategoryException("Category " + categoria + "is invalid!");
        }
    }
    public boolean removeTransacao(String categoria, String nome) throws InvalidCategoryException, InvalidTransactionException {
        if (ValidationModule.isValidCategory(categoria, data)) {
            if (ValidationModule.isValidTransaction(categoria, nome, data)) {
                data.removeTransacao(categoria, nome);
            } else {
                throw new InvalidTransactionException("Transaction " + categoria + " :: " + nome);
            }
        } else {
            throw new InvalidCategoryException("Category " + categoria + " is invalid!");
        }
        return false;
    }
    public boolean editarTransacao(String categoria, String nomeAtual, String novoNome, double montante, Date data) throws InvalidCategoryException, InvalidTransactionException, InvalidDateException, InvalidAmmountException, InvalidNameException {
        if (ValidationModule.isValidTransaction(categoria, nomeAtual, this.data)) {
            if (ValidationModule.isValidName(novoNome)){
                if (ValidationModule.isValidAmmount(montante)) {
                    if (ValidationModule.isValidDate(data)) {
                        return this.data.editaTransacao(categoria, nomeAtual, novoNome, montante, data);
                    } else {
                        throw new InvalidDateException("Data " + data + "is invalid!");
                    }
                }else {
                    throw new InvalidAmmountException("Ammount " + montante + "is invalid!");
                }
            }
            else{
                throw new InvalidNameException("Name " + novoNome + "is invalid!");
            }
        }
        else{
            throw new InvalidTransactionException("Transaction " + nomeAtual + " is invalid");
        }
    }
    public boolean editarOrcamento(String categoria, double orçamento) throws InvalidCategoryException, InvalidAmmountException {
        if (ValidationModule.isValidAmmount(orçamento)) {
            if (ValidationModule.isValidExpensesCategory(categoria, data)) {
                CategoriaDespesas categoria2 = (CategoriaDespesas) data.getCategoria(categoria);
                categoria2.setOrcamento(orçamento);
                return true;
            } else {
                throw new InvalidCategoryException();
            }
        } else {
            throw new InvalidAmmountException();
        }
    }
}
