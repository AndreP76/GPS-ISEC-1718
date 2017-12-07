package com.gps.g13.expensestracker.gestaodedados;

import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidCategoryException;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by andre on 12/7/17.
 */

class ValidationModule {
    public static boolean isValidDate(Date data) {
        GregorianCalendar GC = new GregorianCalendar();
        Date Today = GC.getTime();
        GC.set(GregorianCalendar.DAY_OF_MONTH, 1);
        Date BeginingOfMonth = GC.getTime();

        return data.compareTo(Today) <= 0 && data.compareTo(BeginingOfMonth) > 0;
    }

    public static boolean isValidAmmount(double montante) {
        return montante > 0;
    }

    public static boolean isValidName(String nome) {
        return nome.trim().length() > 0;
    }

    public static boolean isValidCategory(String categoria, Dados data) {
        return categoria != null && data.containsCategory(categoria);
    }

    public static boolean isValidTransaction(String categoria, String nome, Dados data) throws InvalidCategoryException {
        if (isValidCategory(categoria, data)) {
            return data.getCategoria(categoria).containsTransacao(nome);
        } else throw new InvalidCategoryException("Category " + categoria + " is invalid!");
    }

    public static boolean isValidExpensesCategory(String categoria, Dados data) {
        if (isValidCategory(categoria, data)) {
            Categoria c = null;
            try {
                c = data.getCategoria(categoria);
            } catch (InvalidCategoryException e) {
                return false;
            }
            return c instanceof CategoriaDespesas;
        }
        return false;
    }
}
