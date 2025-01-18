package br.com.favoritmanager.core.exception;

public class ValueIsEmptyOrBlankException extends RuntimeException {

    private static final String message = "Value is empty or blank: ";

    public ValueIsEmptyOrBlankException(String propertyValue) {
        super(message.concat(propertyValue));
    }
}
