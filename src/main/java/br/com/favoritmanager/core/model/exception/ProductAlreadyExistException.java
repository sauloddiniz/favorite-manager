package br.com.favoritmanager.core.model.exception;

public class ProductAlreadyExistException extends RuntimeException {

    private static final String message = "Product already exist in favorites: ";

    public ProductAlreadyExistException(String productId) {
        super(message.concat(productId));
    }
}
