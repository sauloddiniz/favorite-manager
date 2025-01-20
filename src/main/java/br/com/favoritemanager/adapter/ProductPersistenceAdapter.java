package br.com.favoritemanager.adapter;

import br.com.favoritemanager.adapter.output.ProductPersistencePort;
import br.com.favoritemanager.adapter.persistence.entity.ProductEntity;
import br.com.favoritemanager.adapter.persistence.entity.mapper.ProductMapper;
import br.com.favoritemanager.adapter.persistence.repository.ProductRepository;
import br.com.favoritemanager.core.model.Product;
import jakarta.transaction.Transactional;
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
        ProductMapper.toModel(productRepository.save(productEntity));
    }

    @Override
    @Transactional
    public void deleteProduct(Product product) {
        ProductEntity productEntity = ProductMapper.toEntity(product);
        productRepository.deleteByClientIdAndProductExternalId(productEntity.getClient().getClientId(),
                productEntity.getProductExternalId());
    }

}
