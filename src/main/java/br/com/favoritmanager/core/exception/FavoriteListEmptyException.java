package br.com.favoritmanager.core.exception;

public class FavoriteListEmptyException extends RuntimeException {

    private static final String message = "Favorite list empty";

    public FavoriteListEmptyException() {
        super(message);
    }
}
