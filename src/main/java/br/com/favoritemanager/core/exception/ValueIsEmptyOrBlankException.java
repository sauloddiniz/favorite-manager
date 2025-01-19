package br.com.favoritemanager.core.exception;

public class ValueIsEmptyOrBlankException extends RuntimeException {

    public ValueIsEmptyOrBlankException(String message) {
        super(message);
    }
}
