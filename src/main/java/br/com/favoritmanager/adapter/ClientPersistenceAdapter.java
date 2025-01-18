package br.com.favoritmanager.adapter;

import br.com.favoritmanager.adapter.output.ClientPersistencePort;
import br.com.favoritmanager.adapter.persistence.entity.ClientEntity;
import br.com.favoritmanager.adapter.persistence.entity.mapper.ClientMapper;
import br.com.favoritmanager.adapter.persistence.repository.ClientRepository;
import br.com.favoritmanager.core.model.Client;
import br.com.favoritmanager.core.model.exception.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

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
                .orElseThrow(() -> new EmptyResultDataAccessException(id.toString()));
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.findById(id)
                .ifPresentOrElse(clientRepository::delete,
                        () -> {
                            throw new EmptyResultDataAccessException(id.toString());
                        });
    }
}
