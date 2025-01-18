package br.com.favoritmanager.adapter;

import br.com.favoritmanager.adapter.output.ProductPersistencePort;
import br.com.favoritmanager.adapter.persistence.entity.ProductEntity;
import br.com.favoritmanager.adapter.persistence.entity.mapper.ProductMapper;
import br.com.favoritmanager.adapter.persistence.repository.ProductRepository;
import br.com.favoritmanager.core.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductPersistenceAdapter implements ProductPersistencePort {

    private final ProductRepository productRepository;

    public ProductPersistenceAdapter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void saveProduct(Product product) {
        ProductEntity productEntity = ProductMapper.toEntity(product);
        productRepository.save(productEntity);
    }
}
