package br.com.favoritemanager.application.usecase;

import br.com.favoritemanager.adapter.input.DTO.ClientAndListFavoritesResponseDTO;
import br.com.favoritemanager.adapter.input.DTO.ClientResponseDTO;
import br.com.favoritemanager.adapter.input.DTO.ClientRequestDTO;

import java.util.List;

public interface ClientUseCase {
    ClientResponseDTO saveClient(ClientRequestDTO clientRequestDTO);
    ClientResponseDTO getClient(Long id);
    void updateClient(Long id, ClientRequestDTO clientRequestDTO);
    void deleteClient(Long id);
    List<ClientAndListFavoritesResponseDTO> getAllClients(boolean favorites);
}
