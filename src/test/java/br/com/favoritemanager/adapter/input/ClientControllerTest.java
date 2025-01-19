package br.com.favoritemanager.adapter.input;

import br.com.favoritemanager.adapter.persistence.entity.ClientEntity;
import br.com.favoritemanager.adapter.persistence.repository.ClientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientRepository clientRepository;

    @Test
    @DisplayName("Create client successfully when input data is valid")
    void testCreateClient_Success() throws Exception {

        ClientEntity clientEntity =
                ClientEntity.builder()
                        .clientId(1L)
                        .name("Bruce Wayne")
                        .email("batman@morcegao.com")
                        .build();

        String requestPayload = """
                    {
                        "name": "Bruce Wayne",
                        "email": "batman@morcegao.com"
                    }
                """;

        when(clientRepository.save(Mockito.any()))
                .thenReturn(clientEntity);

        mockMvc.perform(MockMvcRequestBuilders.post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestPayload))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/client/1"));
    }

    @Test
    @DisplayName("throw error when client input data is invalid")
    void testCreateClient_InvalidPayload() throws Exception {
        String requestPayload = """
                    {
                        "name": "",
                        "email": "invalid-email"
                    }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestPayload))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should throw error when email is already registered")
    void ShouldThrowErrorWhenEmailIsAlreadyRegistered() throws Exception {
        String requestPayload = """
                    {
                        "name": "Bruce Wayne",
                        "email": "batman@morcegao.com"
                    }
                """;

        when(clientRepository.existsByEmail(anyString()))
                .thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestPayload))
                .andExpect(status().is(409));
    }


    @Test
    @DisplayName("Retrieve client successfully when ID matches an existing client")
    void testGetClient_Success() throws Exception {

        ClientEntity clientEntity =
                ClientEntity.builder()
                        .clientId(1L)
                        .name("Bruce Wayne")
                        .email("batman@morcegao.com")
                        .build();

        when(clientRepository.findById(1L))
                .thenReturn(Optional.of(clientEntity));

        mockMvc.perform(MockMvcRequestBuilders.get("/client/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Return 404 status code when client is not found")
    void testGetClient_NotFound() throws Exception {

        when(clientRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/client/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Update client successfully when input data is valid")
    void testUpdateClient_Success() throws Exception {
        String requestPayload = """
                    {
                        "name": "Clark Kent",
                        "email": "superman@justiceleague.com"
                    }
                """;

        ClientEntity existingClient = ClientEntity.builder()
                .clientId(1L)
                .name("Bruce Wayne")
                .email("batman@morcegao.com")
                .build();

        when(clientRepository.findById(anyLong()))
                .thenReturn(Optional.of(existingClient));
        when(clientRepository.save(Mockito.any()))
                .thenReturn(existingClient);

        mockMvc.perform(MockMvcRequestBuilders.put("/client/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestPayload))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Throw 404 Not Found when updating client with non-existent ID")
    void testUpdateClient_NotFound() throws Exception {
        String requestPayload = """
                    {
                        "name": "Clark Kent",
                        "email": "superman@justiceleague.com"
                    }
                """;

        when(clientRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.put("/client/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestPayload))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Throw 409 Conflict when email is already registered on another client")
    void testUpdateClient_EmailConflict() throws Exception {
        String requestPayload = """
                    {
                        "name": "Clark Kent",
                        "email": "batman@morcegao.com"
                    }
                """;

        ClientEntity existingClient = ClientEntity.builder()
                .clientId(1L)
                .name("Bruce Wayne")
                .email("batman@morcegao.com.br")
                .build();

        when(clientRepository.findById(anyLong()))
                .thenReturn(Optional.of(existingClient));
        when(clientRepository.existsByEmail(anyString()))
                .thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put("/client/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestPayload))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Delete client successfully when ID matches an existing client")
    void testDeleteClient_Success() throws Exception {
        ClientEntity clientEntity =
                ClientEntity.builder()
                        .clientId(1L)
                        .name("Bruce Wayne")
                        .email("batman@morcegao.com")
                        .build();

        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientEntity));
        Mockito.doNothing().when(clientRepository).delete(clientEntity);

        mockMvc.perform(MockMvcRequestBuilders.delete("/client/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Throw 404 Not Found when attempting to delete a non-existent client")
    void testDeleteClient_NotFound() throws Exception {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.delete("/client/1"))
                .andExpect(status().isNotFound());
    }
}
