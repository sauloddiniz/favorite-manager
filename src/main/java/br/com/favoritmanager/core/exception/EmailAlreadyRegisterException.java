package br.com.favoritmanager.core.exception;

public class EmailAlreadyRegisterException extends RuntimeException {

    private static final String message = "Email already register: ";

    public EmailAlreadyRegisterException(String email) {
        super(message.concat(email));
    }
}
