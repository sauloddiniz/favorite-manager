package br.com.favoritemanager.core.exception;

public class EmailAlreadyRegisterException extends RuntimeException {

    private static final String MESSAGE = "Email already register: ";

    public EmailAlreadyRegisterException(String email) {
        super(MESSAGE.concat(email));
    }
}
