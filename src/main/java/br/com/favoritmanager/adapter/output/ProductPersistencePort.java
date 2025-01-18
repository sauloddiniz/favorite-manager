package br.com.favoritmanager.adapter.output;

import br.com.favoritmanager.core.model.Product;

public interface ProductPersistencePort {
    void saveProduct(Product product);
}
