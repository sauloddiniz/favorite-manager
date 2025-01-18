package br.com.favoritmanager.core.model.exception;

public class EmptyResultDataAccessException extends RuntimeException {

    private static final String message = "Empty result data access :";

    public EmptyResultDataAccessException(String id) {
        super(message.concat(id));
    }
}
