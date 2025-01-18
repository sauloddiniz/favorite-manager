package br.com.favoritmanager.core.exception;

public class ProductAlreadyExistException extends RuntimeException {

    private static final String MESSAGE = "Product already exist in favorites: ";

    public ProductAlreadyExistException(String productId) {
        super(MESSAGE.concat(productId));
    }
}
