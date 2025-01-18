package br.com.favoritmanager.adapter.output;

import br.com.favoritmanager.core.model.Client;

public interface ClientPersistencePort {
    Client saveClient(Client client);
    boolean existsClient(String email);
    Client getClientById(Long id);
    void deleteClient(Long id);
}
