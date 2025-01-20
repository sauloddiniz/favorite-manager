package br.com.favoritemanager.adapter.input;

import br.com.favoritemanager.adapter.input.DTO.ClientAndListFavoritesResponseDTO;
import br.com.favoritemanager.adapter.persistence.entity.ClientEntity;
import br.com.favoritemanager.adapter.persistence.entity.ProductEntity;
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

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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
    @DisplayName("Return a list of clients with their favorites when 'favorites' parameter is true")
    void testGetAllClients_Success_WithFavorites() throws Exception {

        List<ClientEntity> clientMock = getClientMock();

        when(clientRepository.findAll()).thenReturn(clientMock);

        mockMvc.perform(MockMvcRequestBuilders.get("/client")
                        .queryParam("favorites", "true")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(clientMock.size()))

                .andExpect(jsonPath("$[0].name").value("Bruce Wayne"))
                .andExpect(jsonPath("$[0].email").value("batman@morcegao.com"))
                .andExpect(jsonPath("$[0].favoritesProducts.length()").value(2))
                .andExpect(jsonPath("$[0].favoritesProducts[0].id").value(101))
                .andExpect(jsonPath("$[0].favoritesProducts[0].title").value("Batsuit"))

                .andExpect(jsonPath("$[1].name").value("Clark Kent"))
                .andExpect(jsonPath("$[1].email").value("superman@kent.com"))
                .andExpect(jsonPath("$[1].favoritesProducts.length()").value(1))

                .andExpect(jsonPath("$[2].name").value("Diana Prince"))
                .andExpect(jsonPath("$[2].email").value("wonderwoman@amazon.com"))
                .andExpect(jsonPath("$[2].favoritesProducts.length()").value(2))
                .andExpect(jsonPath("$[2].favoritesProducts[0].id").value(302))
                .andExpect(jsonPath("$[2].favoritesProducts[0].title").value("Bracelets of Submission"));
    }

    @Test
    @DisplayName("Return a list of clients without favorites when 'favorites' parameter is false")
    void testGetAllClients_Success_WithoutFavorites() throws Exception {

        List<ClientEntity> clientMock = getClientMock();

        when(clientRepository.findAll()).thenReturn(clientMock);

        mockMvc.perform(MockMvcRequestBuilders.get("/client")
                        .queryParam("favorites", "false")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(clientMock.size()))
                .andExpect(jsonPath("$[0].favoritesProducts.length()").value(0))
                .andExpect(jsonPath("$[1].favoritesProducts.length()").value(0))
                .andExpect(jsonPath("$[2].favoritesProducts.length()").value(0));
    }

    @Test
    @DisplayName("Return an empty list of clients when 'favorites' parameter is false")
    void testGetAllClients_EmptyList_WithoutFavorites() throws Exception {

        List<ClientEntity> emptyClientMock = List.of();

        when(clientRepository.findAll()).thenReturn(emptyClientMock);

        mockMvc.perform(MockMvcRequestBuilders.get("/client")
                        .queryParam("favorites", "false")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0))
                .andExpect(content().json("[]"));
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

    private static List<ClientEntity> getClientMock() {
        return List.of(
                ClientEntity.builder()
                        .clientId(1L)
                        .name("Bruce Wayne")
                        .email("batman@morcegao.com")
                        .favoriteProducts(Set.of(
                                ProductEntity.builder()
                                        .productId(101L)
                                        .productExternalId(101L)
                                        .title("Batsuit")
                                        .image("batsuit-image.jpg")
                                        .price(1000000.0)
                                        .reviewScore("5.0")
                                        .build(),
                                ProductEntity.builder()
                                        .productId(102L)
                                        .productExternalId(102L)
                                        .title("Batmobile")
                                        .image("batmobile-image.jpg")
                                        .price(5000000.0)
                                        .reviewScore("4.8")
                                        .build()
                        ))
                        .build(),

                ClientEntity.builder()
                        .clientId(2L)
                        .name("Clark Kent")
                        .email("superman@kent.com")
                        .favoriteProducts(Set.of(
                                ProductEntity.builder()
                                        .productId(201L)
                                        .productExternalId(201L)
                                        .title("Super Cape")
                                        .image("super-cape-image.jpg")
                                        .price(200000.0)
                                        .reviewScore("4.9")
                                        .build()
                        ))
                        .build(),

                ClientEntity.builder()
                        .clientId(3L)
                        .name("Diana Prince")
                        .email("wonderwoman@amazon.com")
                        .favoriteProducts(Set.of(
                                ProductEntity.builder()
                                        .productId(301L)
                                        .productExternalId(301L)
                                        .title("Lasso of Truth")
                                        .image("lasso-image.jpg")
                                        .price(1500000.0)
                                        .reviewScore("5.0")
                                        .build(),
                                ProductEntity.builder()
                                        .productId(302L)
                                        .productExternalId(302L)
                                        .title("Bracelets of Submission")
                                        .image("bracelets-image.jpg")
                                        .price(400000.0)
                                        .reviewScore("4.7")
                                        .build()
                        ))
                        .build()
        );
    }
}
