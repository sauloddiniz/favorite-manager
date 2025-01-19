package br.com.favoritemanager.application.usecase;

import br.com.favoritemanager.adapter.input.DTO.ClientAndListFavoritesResponseDTO;
import br.com.favoritemanager.adapter.input.DTO.ClientResponseDTO;
import br.com.favoritemanager.adapter.input.DTO.ClientRequestDTO;
import br.com.favoritemanager.adapter.output.ClientPersistencePort;
import br.com.favoritemanager.core.model.Client;
import br.com.favoritemanager.core.exception.EmailAlreadyRegisterException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientUseCaseImpl implements ClientUseCase {

    private final ClientPersistencePort clientPersistencePort;

    public ClientUseCaseImpl(ClientPersistencePort clientPersistencePort) {
        this.clientPersistencePort = clientPersistencePort;
    }

    @Override
    public ClientResponseDTO saveClient(ClientRequestDTO clientRequestDTO) {
        Client client = new Client(clientRequestDTO.name(), clientRequestDTO.email());
        validClientEmail(client.getEmail());
        client = clientPersistencePort.saveClient(client);
        return ClientResponseDTO.toResponseDTO(client);
    }

    @Override
    public ClientResponseDTO getClient(Long id) {
        return ClientResponseDTO
                .toResponseDTO(clientPersistencePort.
                        getClientById(id));
    }

    @Override
    public void updateClient(Long id, ClientRequestDTO clientRequestDTO) {
        Client client = clientPersistencePort.getClientById(id);
        if (isEmailNotEquals(clientRequestDTO, client)) {
            validClientEmail(clientRequestDTO.email());
        }
        clientPersistencePort.saveClient(new Client(client.getId(), clientRequestDTO.name(), clientRequestDTO.email()));
    }

    @Override
    public void deleteClient(Long id) {
        clientPersistencePort.deleteClient(id);
    }

    @Override
    public List<ClientAndListFavoritesResponseDTO> getAllClients(boolean favorites) {
        List<Client> clientList = clientPersistencePort.findAll();
        return clientList.stream().map(ClientAndListFavoritesResponseDTO::toResponse).toList();
    }

    private static boolean isEmailNotEquals(ClientRequestDTO clientRequestDTO, Client client) {
        return !client.getEmail().equals(clientRequestDTO.email());
    }

    private void validClientEmail(String email) {
        if (clientPersistencePort.existsClient(email)) {
            throw new EmailAlreadyRegisterException(email);
        }
    }
}
