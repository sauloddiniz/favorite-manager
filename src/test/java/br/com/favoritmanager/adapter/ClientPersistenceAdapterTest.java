package br.com.favoritmanager.adapter;

import br.com.favoritmanager.adapter.persistence.entity.ClientEntity;
import br.com.favoritmanager.adapter.persistence.repository.ClientRepository;
import br.com.favoritmanager.core.model.Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ClientPersistenceAdapterTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientPersistenceAdapter clientPersistenceAdapter;

    @Test
    @DisplayName("Should save client Successfully")
    void shouldSaveClientSuccessfully() {
        Client client = new Client("Bruce Wayne", "batman@morcegao.com");
        ClientEntity mockedEntity = new ClientEntity();
        mockedEntity.setClientId(1L);
        mockedEntity.setName(client.getName());
        mockedEntity.setEmail(client.getEmail());

        Mockito.when(clientRepository.save(any(ClientEntity.class)))
                .thenReturn(mockedEntity);

        Client savedClient = clientPersistenceAdapter.saveClient(client);

        assertEquals(client.getName(), savedClient.getName());
        assertEquals(client.getEmail(), savedClient.getEmail());
    }
}
