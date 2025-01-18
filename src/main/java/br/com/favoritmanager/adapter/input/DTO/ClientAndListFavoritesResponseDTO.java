package br.com.favoritmanager.adapter.input.DTO;

import br.com.favoritmanager.adapter.feign.client.DTO.ProductResponseDTO;
import br.com.favoritmanager.core.model.Client;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public record ClientAndListFavoritesResponseDTO(String name, String email, List<ProductResponseDTO> favoritesProducts) {

    public static ClientAndListFavoritesResponseDTO toResponse(Client client) {
        return new ClientAndListFavoritesResponseDTO(client.getName(), client.getEmail(),
                Optional.ofNullable(client.getFavoriteProducts())
                        .orElse(Set.of())
                        .stream()
                        .map(ProductResponseDTO::toResponse)
                        .toList());
    }
}
