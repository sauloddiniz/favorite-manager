package br.com.favoritemanager.adapter;

import br.com.favoritemanager.adapter.output.ClientPersistencePort;
import br.com.favoritemanager.adapter.persistence.entity.ClientEntity;
import br.com.favoritemanager.adapter.persistence.entity.mapper.ClientMapper;
import br.com.favoritemanager.adapter.persistence.repository.ClientRepository;
import br.com.favoritemanager.core.model.Client;
import br.com.favoritemanager.core.exception.ClientNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ClientPersistenceAdapter implements ClientPersistencePort {

    private final ClientRepository clientRepository;

    public ClientPersistenceAdapter(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client saveClient(Client client) {
        ClientEntity entity = ClientMapper.toEntity(client);
        return ClientMapper.toModel(clientRepository.save(entity));
    }

    @Override
    public boolean existsClient(String email) {
        return clientRepository.existsByEmail(email);
    }

    @Override
    public Client getClientById(Long id) {
        Optional<ClientEntity> entity = clientRepository.findById(id);
        return entity.map(ClientMapper::toModel)
                .orElseThrow(() -> new ClientNotFoundException(id.toString()));
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.findById(id)
                .ifPresentOrElse(clientRepository::delete,
                        () -> {
                            throw new ClientNotFoundException(id.toString());
                        });
    }

    @Override
    public List<Client> findAll() {
        List<ClientEntity> clientEntities = clientRepository.findAll();
        return clientEntities.stream().map(ClientMapper::toModel).toList();
    }
}
