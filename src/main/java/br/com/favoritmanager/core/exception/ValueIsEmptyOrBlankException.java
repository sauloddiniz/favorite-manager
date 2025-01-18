package br.com.favoritmanager.core.exception;

public class ValueIsEmptyOrBlankException extends RuntimeException {

    private static final String MESSAGE = "Value is empty or blank: ";

    public ValueIsEmptyOrBlankException(String propertyValue) {
        super(MESSAGE.concat(propertyValue));
    }
}
