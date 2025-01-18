package br.com.favoritmanager.adapter.output;

import br.com.favoritmanager.core.model.Client;

import java.util.List;

public interface ClientPersistencePort {
    Client saveClient(Client client);
    boolean existsClient(String email);
    Client getClientById(Long id);
    void deleteClient(Long id);
    List<Client> findAll();
}
