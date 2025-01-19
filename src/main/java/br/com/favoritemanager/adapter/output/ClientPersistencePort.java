package br.com.favoritemanager.adapter.output;

import br.com.favoritemanager.core.model.Client;

import java.util.List;

public interface ClientPersistencePort {
    Client saveClient(Client client);
    boolean existsClient(String email);
    Client getClientById(Long id);
    void deleteClient(Long id);
    List<Client> findAll();
}
