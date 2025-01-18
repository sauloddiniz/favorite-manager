package br.com.favoritmanager.adapter;

import br.com.favoritmanager.adapter.output.ProductPersistencePort;
import br.com.favoritmanager.adapter.persistence.entity.ProductEntity;
import br.com.favoritmanager.adapter.persistence.entity.mapper.ProductMapper;
import br.com.favoritmanager.adapter.persistence.repository.ProductRepository;
import br.com.favoritmanager.core.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;

@Component
public class ProductPersistenceAdapter implements ProductPersistencePort {

    private final ProductRepository productRepository;

    public ProductPersistenceAdapter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product saveProduct(Product product) {
        ProductEntity productEntity = ProductMapper.toEntity(product);
        return ProductMapper.toModel(productRepository.save(productEntity));
    }

    @Override
    @Transactional
    public void deleteProduct(Product product) {
        ProductEntity productEntity = ProductMapper.toEntity(product);
        productRepository.deleteByClientIdAndProductIdLuizaLabs(productEntity.getClient().getClientId(), productEntity.getProductIdLuizaLabs());
    }

}
