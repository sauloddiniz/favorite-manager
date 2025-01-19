package br.com.favoritemanager.adapter.persistence.entity.mapper;

import br.com.favoritemanager.adapter.persistence.entity.ClientEntity;
import br.com.favoritemanager.adapter.persistence.entity.ProductEntity;
import br.com.favoritemanager.core.model.Client;
import br.com.favoritemanager.core.model.Product;

import java.util.Optional;

public class ProductMapper {
    private ProductMapper() {}

    public static ProductEntity toEntity(Product product) {
        return ProductEntity.builder()
                .productId(product.getId())
                .productIdLuizaLabs(product.getProductIdLuizaLabs())
                .title(product.getTitle())
                .price(product.getPrice())
                .image(product.getImage())
                .reviewScore(product.getReviewScore())
                .client(ClientEntity.builder()
                        .clientId(Optional.ofNullable(product.getClient()).map(Client::getId).orElse(null))
                        .build())
                .build();
    }

    public static Product toModel(ProductEntity productEntity) {
        return new Product(
                productEntity.getProductId(),
                productEntity.getProductIdLuizaLabs(),
                productEntity.getImage(),
                productEntity.getPrice(),
                productEntity.getTitle(),
                productEntity.getReviewScore());
    }
}
