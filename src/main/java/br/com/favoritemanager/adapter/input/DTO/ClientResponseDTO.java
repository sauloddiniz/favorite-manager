package br.com.favoritemanager.adapter.input.DTO;

import br.com.favoritemanager.core.model.Client;

public record ClientResponseDTO(Long id, String name, String email) {

    public static ClientResponseDTO toResponseDTO(Client client) {
        return new ClientResponseDTO(client.getId(), client.getName(), client.getEmail());
    }
}
