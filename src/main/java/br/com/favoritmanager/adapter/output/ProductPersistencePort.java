package br.com.favoritmanager.adapter.output;

import br.com.favoritmanager.core.model.Product;

public interface ProductPersistencePort {
    Product saveProduct(Product product);
    void deleteProduct(Product product);
}
