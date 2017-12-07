package com.gps.g13.expensestracker.gestaodedados;

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
        return data.containsCategory(categoria);
    }
}
