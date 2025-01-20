package br.com.favoritemanager.adapter.feign.client.DTO;

import br.com.favoritemanager.core.model.Product;

public record ProductResponseDTO(Long id, String image, Double price, String title, String reviewScore) {

    public static Product toProduct(ProductResponseDTO productResponseDTO) {
        return new Product(productResponseDTO.id(), productResponseDTO.image(), productResponseDTO.price(), productResponseDTO.title(), productResponseDTO.reviewScore());
    }

    public static ProductResponseDTO toResponse(Product product) {
        return new ProductResponseDTO(product.getProductExternalId(),
                product.getImage(),
                product.getPrice(),
                product.getTitle(),
                product.getReviewScore());
    }
}
