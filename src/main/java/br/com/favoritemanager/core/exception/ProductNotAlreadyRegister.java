package br.com.favoritemanager.core.exception;

public class ProductNotAlreadyRegister extends RuntimeException {

    private static final String MESSAGE = "Product not already register: ";

    public ProductNotAlreadyRegister(String productId) {
        super(MESSAGE.concat(productId));
    }
}
