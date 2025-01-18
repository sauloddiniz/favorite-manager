package br.com.favoritmanager.core.exception;

public class ClientNotFoundException extends RuntimeException {

    private static final String message = "Client not found :";

    public ClientNotFoundException(String id) {
        super(message.concat(id));
    }
}
