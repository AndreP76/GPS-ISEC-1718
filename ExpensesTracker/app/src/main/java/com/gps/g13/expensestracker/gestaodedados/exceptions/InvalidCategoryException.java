package com.gps.g13.expensestracker.gestaodedados.exceptions;

/**
 * Created by andre on 12/7/17.
 */

public class InvalidCategoryException extends Throwable {
    public InvalidCategoryException(String s) {
        super(s);
    }

    public InvalidCategoryException() {
        super();
    }
}
