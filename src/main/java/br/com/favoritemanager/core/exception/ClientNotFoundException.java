package br.com.favoritemanager.core.exception;

public class ClientNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Client not found :";

    public ClientNotFoundException(String id) {
        super(MESSAGE.concat(id));
    }
}
