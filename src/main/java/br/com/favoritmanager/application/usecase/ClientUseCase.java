package br.com.favoritmanager.application.usecase;

import br.com.favoritmanager.adapter.input.DTO.ClientResponseDTO;
import br.com.favoritmanager.adapter.input.DTO.ClientRequestDTO;

public interface ClientUseCase {
    ClientResponseDTO saveClient(ClientRequestDTO clientRequestDTO);
    ClientResponseDTO getClient(Long id);
    void updateClient(Long id, ClientRequestDTO clientRequestDTO);
    void deleteClient(Long id);
}
