package br.com.favoritemanager.adapter;

import br.com.favoritemanager.adapter.persistence.entity.ClientEntity;
import br.com.favoritemanager.adapter.persistence.repository.ClientRepository;
import br.com.favoritemanager.core.exception.ClientNotFoundException;
import br.com.favoritemanager.core.model.Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientPersistenceAdapterTest {

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

        when(clientRepository.save(any(ClientEntity.class)))
                .thenReturn(mockedEntity);

        Client savedClient = clientPersistenceAdapter.saveClient(client);

        assertEquals(client.getName(), savedClient.getName());
        assertEquals(client.getEmail(), savedClient.getEmail());
    }

    @Test
    @DisplayName("Should return true if client exists")
    void shouldReturnTrueIfClientExists() {
        String email = "existing_client@example.com";

        when(clientRepository.existsByEmail(email))
                .thenReturn(true);

        boolean exists = clientPersistenceAdapter.existsClient(email);

        assertTrue(exists);
    }

    @Test
    @DisplayName("Should return false if client does not exist")
    void shouldReturnFalseIfClientDoesNotExist() {
        String email = "non_existing_client@example.com";

        when(clientRepository.existsByEmail(email))
                .thenReturn(false);

        boolean exists = clientPersistenceAdapter.existsClient(email);

        assertFalse(exists);
    }

    @Test
    @DisplayName("Should return client when client ID exists")
    void shouldReturnClientWhenClientIdExists() {
        Long clientId = 1L;
        ClientEntity mockedEntity = new ClientEntity();
        mockedEntity.setClientId(clientId);
        mockedEntity.setName("Bruce Wayne");
        mockedEntity.setEmail("batman@morcegao.com");

        when(clientRepository.findById(clientId))
                .thenReturn(Optional.of(mockedEntity));

        Client client = clientPersistenceAdapter.getClientById(clientId);

        assertNotNull(client);
        assertEquals(mockedEntity.getClientId(), client.getId());
        assertEquals(mockedEntity.getName(), client.getName());
        assertEquals(mockedEntity.getEmail(), client.getEmail());
    }

    @Test
    @DisplayName("Should throw ClientNotFoundException when client ID does not exist")
    void shouldThrowClientNotFoundExceptionWhenClientIdDoesNotExist() {
        Long clientId = 2L;

        when(clientRepository.findById(clientId))
                .thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientPersistenceAdapter.getClientById(clientId));
    }

    @Test
    @DisplayName("Should delete client successfully")
    void shouldDeleteClientSuccessfully() {
        Long clientId = 1L;
        ClientEntity mockedEntity = new ClientEntity();
        mockedEntity.setClientId(clientId);
        mockedEntity.setName("Bruce Wayne");
        mockedEntity.setEmail("batman@morcegao.com");

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(mockedEntity));
        Mockito.doNothing().when(clientRepository).delete(mockedEntity);

        assertDoesNotThrow(() -> clientPersistenceAdapter.deleteClient(clientId));
        Mockito.verify(clientRepository).delete(mockedEntity);
    }

    @Test
    @DisplayName("Should throw ClientNotFoundException when deleting non-existent client")
    void shouldThrowClientNotFoundExceptionWhenDeletingNonExistentClient() {
        Long clientId = 2L;

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientPersistenceAdapter.deleteClient(clientId));
        Mockito.verify(clientRepository, Mockito.never()).delete(any());
    }
}
