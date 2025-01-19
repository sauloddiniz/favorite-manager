package br.com.favoritemanager.core.exception;

public class FavoriteListEmptyException extends RuntimeException {

    private static final String MESSAGE = "Favorite list empty";

    public FavoriteListEmptyException() {
        super(MESSAGE);
    }
}
