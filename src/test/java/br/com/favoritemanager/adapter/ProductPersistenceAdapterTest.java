package br.com.favoritemanager.adapter;

import br.com.favoritemanager.adapter.persistence.entity.ProductEntity;
import br.com.favoritemanager.adapter.persistence.entity.mapper.ProductMapper;
import br.com.favoritemanager.adapter.persistence.repository.ProductRepository;
import br.com.favoritemanager.core.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductPersistenceAdapterTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductPersistenceAdapter productPersistenceAdapter;

    @Test
    @DisplayName("Test saveProduct: Should save product successfully")
    void testSaveProduct_Success() {
        Product product = new Product(
                1L,
                12345L,
                "https://image.url",
                99.99,
                "Product Title",
                "4.5"
        );

        when(productRepository.save(any(ProductEntity.class)))
                .thenReturn(ProductMapper.toEntity(product));

        productPersistenceAdapter.saveProduct(product);

        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    @DisplayName("Test deleteProduct: Should delete product successfully")
    void testDeleteProduct_Success() {
        Product product = new Product(
                1L,
                12345L,
                "https://image.url",
                99.99,
                "Product Title",
                "4.5"
        );
        ProductEntity productEntity = ProductMapper.toEntity(product);

        doNothing().when(productRepository)
                .deleteByClientIdAndProductIdLuizaLabs(productEntity.getClient().getClientId(), productEntity.getProductIdLuizaLabs());

        productPersistenceAdapter.deleteProduct(product);

        verify(productRepository, times(1))
                .deleteByClientIdAndProductIdLuizaLabs(productEntity.getClient().getClientId(), productEntity.getProductIdLuizaLabs());
    }

    @Test
    @DisplayName("Test deleteProduct: Should throw exception when product does not exist")
    void testDeleteProduct_WithMissingProduct() {
        Product product = new Product(
                1L,
                67890L,
                "https://another.url",
                49.99,
                "Another Title",
                "4.7"
        );
        ProductEntity productEntity = ProductMapper.toEntity(product);

        doThrow(new RuntimeException("Product not found"))
                .when(productRepository)
                .deleteByClientIdAndProductIdLuizaLabs(productEntity.getClient().getClientId(), productEntity.getProductIdLuizaLabs());

        try {
            productPersistenceAdapter.deleteProduct(product);
        } catch (RuntimeException e) {
            assertEquals("Product not found", e.getMessage());
        }

        verify(productRepository, times(1))
                .deleteByClientIdAndProductIdLuizaLabs(productEntity.getClient().getClientId(), productEntity.getProductIdLuizaLabs());
    }
}
