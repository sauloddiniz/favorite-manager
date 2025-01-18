package br.com.favoritmanager.adapter.feign.client.DTO;

import br.com.favoritmanager.core.model.Product;

import java.util.List;

public record ProductResponseDTO(Long id, String image, Double price, String title, String reviewScore) {

    public static Product toProduct(ProductResponseDTO productResponseDTO) {
        return new Product(productResponseDTO.id(), productResponseDTO.image(), productResponseDTO.price(), productResponseDTO.title(), productResponseDTO.image());
    }

    public static ProductResponseDTO toResponse(Product product) {
        return new ProductResponseDTO(product.getProductIdLuizaLabs(), product.getImage(), product.getPrice(), product.getTitle(), product.getReviewScore());
    }
}
