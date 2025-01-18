package br.com.favoritmanager.application.usecase;

import br.com.favoritmanager.adapter.input.DTO.ClientAndListFavoritesResponseDTO;

public interface ProductUseCase {
    void saveProduct(Long clientId, Long productId);
    ClientAndListFavoritesResponseDTO getProducts(Long clientId);
    void removeFavoriteProduct(Long clientId, Long productIdLuizaLabs);
    ClientAndListFavoritesResponseDTO getFavorite(Long clientId, Long productIdLuizaLabs);
}
