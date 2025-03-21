package br.com.favoritemanager.adapter.input.DTO;

import br.com.favoritemanager.adapter.feign.client.DTO.ProductResponseDTO;
import br.com.favoritemanager.core.model.Client;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public record ClientAndListFavoritesResponseDTO(Long clientId, String name, String email, List<ProductResponseDTO> favoritesProducts) {

    public static ClientAndListFavoritesResponseDTO toResponse(Client client) {
        return new ClientAndListFavoritesResponseDTO(
                client.getId(),
                client.getName(),
                client.getEmail(),
                Optional.ofNullable(client.getFavoriteProducts())
                        .orElse(Set.of())
                        .stream()
                        .map(ProductResponseDTO::toResponse)
                        .toList());
    }
}
