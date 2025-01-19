package br.com.favoritemanager.adapter.output;

import br.com.favoritemanager.core.model.Product;

public interface ProductPersistencePort {
    void saveProduct(Product product);
    void deleteProduct(Product product);
}
