package br.com.favoritmanager.core.exception;

public class ProductNotAlreadyRegister extends RuntimeException {

    private static final String message = "Product not already register: ";

    public ProductNotAlreadyRegister(String productId) {
        super(message.concat(productId));
    }
}
