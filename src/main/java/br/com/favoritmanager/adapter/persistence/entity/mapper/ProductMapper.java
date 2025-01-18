package br.com.favoritmanager.adapter.persistence.entity.mapper;

import br.com.favoritmanager.adapter.persistence.entity.ClientEntity;
import br.com.favoritmanager.adapter.persistence.entity.ProductEntity;
import br.com.favoritmanager.core.model.Client;
import br.com.favoritmanager.core.model.Product;

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
}
