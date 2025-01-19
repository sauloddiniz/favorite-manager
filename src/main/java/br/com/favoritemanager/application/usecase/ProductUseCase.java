package br.com.favoritemanager.application.usecase;

import br.com.favoritemanager.adapter.input.DTO.ClientAndListFavoritesResponseDTO;

public interface ProductUseCase {
    void saveProduct(Long clientId, Long productId);
    ClientAndListFavoritesResponseDTO getProducts(Long clientId);
    void removeFavoriteProduct(Long clientId, Long productId);
    ClientAndListFavoritesResponseDTO getFavorite(Long clientId, Long productId);
}
