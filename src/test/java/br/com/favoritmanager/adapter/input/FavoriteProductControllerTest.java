package br.com.favoritmanager.adapter.input;

import br.com.favoritmanager.adapter.feign.client.DTO.ProductResponseDTO;
import br.com.favoritmanager.adapter.feign.client.ProductClientApiFeign;
import br.com.favoritmanager.adapter.persistence.entity.ClientEntity;
import br.com.favoritmanager.adapter.persistence.entity.ProductEntity;
import br.com.favoritmanager.adapter.persistence.repository.ClientRepository;
import br.com.favoritmanager.adapter.persistence.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;
import java.util.Set;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class FavoriteProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientRepository clientRepository;

    @MockitoBean
    private ProductRepository productRepository;

    @MockitoBean
    private ProductClientApiFeign productClientApiFeign;

    @Test
    @DisplayName("Save favorite product successfully when input data is valid")
    void testSaveFavoriteProduct_Success() throws Exception {

        Optional<ClientEntity> mockClient = Optional.of(ClientEntity.builder()
                .clientId(1L)
                .name("Bruce Wayne")
                .email("batman@morcegao.com")
                .build());

        ProductResponseDTO mockResponseApi =
                new ProductResponseDTO(1L, "http://minhaimage.png", 88.55, "Produto Oficial", "4.5");

        ProductEntity mockProduct = ProductEntity.builder()
                .productId(1L)
                .image("http://minhaimage.png")
                .productIdLuizaLabs(1L)
                .price(88.55)
                .title("Produto Oficial")
                .build();

        String clientId = "1";
        String productIdLuizaLabs = "1";

        when(clientRepository.findById(anyLong()))
                .thenReturn(mockClient);
        when(productClientApiFeign.getProductByIdLuizaLabs(anyLong()))
                .thenReturn(new ResponseEntity<>(mockResponseApi, HttpStatus.OK));
        when(productRepository.save(any(ProductEntity.class)))
                .thenReturn(mockProduct);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/client/{clientId}/favorites/product/{productIdLuizaLabs}", clientId, productIdLuizaLabs))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should throw error when trying to save a product that is already in the favorite list")
    void testSaveFavoriteProduct_AlreadyExists() throws Exception {

        Optional<ClientEntity> mockClient = Optional.of(ClientEntity.builder()
                .clientId(1L)
                .name("Bruce Wayne")
                .email("batman@morcegao.com")
                .favoriteProducts(
                        Set.of(ProductEntity.builder()
                                .productId(1L)
                                .productIdLuizaLabs(1L)
                                .image("http://minhaimage.png")
                                .title("Produto Oficial")
                                .price(88.55)
                                .reviewScore("4.5")
                                .build()))
                .build()
        );

        ProductResponseDTO mockResponseApi =
                new ProductResponseDTO(1L, "http://minhaimage.png", 88.55, "Produto Oficial", "4.5");

        String clientId = "1";
        String productIdLuizaLabs = "1";

        when(clientRepository.findById(anyLong())).thenReturn(mockClient);
        when(productClientApiFeign.getProductByIdLuizaLabs(anyLong()))
                .thenReturn(new ResponseEntity<>(mockResponseApi, HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/client/{clientId}/favorites/product/{productIdLuizaLabs}", clientId, productIdLuizaLabs))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Product already exist in favorites: 1"))
                .andExpect(jsonPath("$.path").value("/client/1/favorites/product/1"))
                .andExpect(jsonPath("$.date").exists());
    }

    @Test
    @DisplayName("Should remove favorite product successfully when input data is valid")
    void testRemoveFavoriteProduct_Success() throws Exception {

        Optional<ClientEntity> mockClient = Optional.of(ClientEntity.builder()
                .clientId(1L)
                .name("Bruce Wayne")
                .email("batman@morcegao.com")
                .favoriteProducts(
                        Set.of(ProductEntity.builder()
                                .productId(1L)
                                .productIdLuizaLabs(1L)
                                .image("http://minhaimage.png")
                                .title("Produto Oficial")
                                .price(88.55)
                                .reviewScore("4.5")
                                .build()))
                .build());

        String clientId = "1";
        String productIdLuizaLabs = "1";

        when(clientRepository.findById(anyLong())).thenReturn(mockClient);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/client/{clientId}/favorites/product/{productIdLuizaLabs}", clientId, productIdLuizaLabs))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should throw error when trying to remove a product not in the favorite list")
    void testRemoveFavoriteProduct_NotFound() throws Exception {

        Optional<ClientEntity> mockClient = Optional.of(ClientEntity.builder()
                .clientId(1L)
                .name("Bruce Wayne")
                .email("batman@morcegao.com")
                .favoriteProducts(
                        Set.of(ProductEntity.builder()
                                .productId(3L)
                                .productIdLuizaLabs(3L)
                                .image("http://minhaimage.png")
                                .title("Produto Oficial")
                                .price(88.55)
                                .reviewScore("4.5")
                                .build()))
                .build());

        String clientId = "1";
        String productIdLuizaLabs = "1";

        when(clientRepository.findById(anyLong())).thenReturn(mockClient);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/client/{clientId}/favorites/product/{productIdLuizaLabs}", clientId, productIdLuizaLabs))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Product not already register: 1"))
                .andExpect(jsonPath("$.path").value("/client/1/favorites/product/1"))
                .andExpect(jsonPath("$.date").exists());
    }

    @Test
    @DisplayName("Should throw error when trying to remove a product from an empty favorite list")
    void testRemoveFavoriteProduct_EmptyFavoriteList() throws Exception {

        Optional<ClientEntity> mockClient = Optional.of(ClientEntity.builder()
                .clientId(1L)
                .name("Bruce Wayne")
                .email("batman@morcegao.com")
                .favoriteProducts(Set.of())
                .build());

        String clientId = "1";
        String productIdLuizaLabs = "1";

        when(clientRepository.findById(anyLong())).thenReturn(mockClient);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/client/{clientId}/favorites/product/{productIdLuizaLabs}", clientId, productIdLuizaLabs))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Favorite list empty"))
                .andExpect(jsonPath("$.path").value("/client/1/favorites/product/1"))
                .andExpect(jsonPath("$.date").exists());
    }

    @Test
    @DisplayName("Should successfully retrieve a favorite product when input data is valid")
    void testGetFavorite_Success() throws Exception {

        Optional<ClientEntity> mockClient = Optional.of(ClientEntity.builder()
                .clientId(1L)
                .name("Bruce Wayne")
                .email("batman@morcegao.com")
                .favoriteProducts(
                        Set.of(ProductEntity.builder()
                                .productId(1L)
                                .productIdLuizaLabs(1L)
                                .image("http://minhaimage.png")
                                .title("Produto Oficial")
                                .price(88.55)
                                .reviewScore("4.5")
                                .build()))
                .build());

        String clientId = "1";
        String productIdLuizaLabs = "1";

        when(clientRepository.findById(anyLong())).thenReturn(mockClient);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/client/{clientId}/favorites/product/{productIdLuizaLabs}", clientId, productIdLuizaLabs))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bruce Wayne"))
                .andExpect(jsonPath("$.email").value("batman@morcegao.com"))
                .andExpect(jsonPath("$.favoritesProducts[0].id").value(1L))
                .andExpect(jsonPath("$.favoritesProducts[0].image").value("http://minhaimage.png"))
                .andExpect(jsonPath("$.favoritesProducts[0].price").value(88.55))
                .andExpect(jsonPath("$.favoritesProducts[0].title").value("Produto Oficial"))
                .andExpect(jsonPath("$.favoritesProducts[0].reviewScore").value("4.5"));
    }

    @Test
    @DisplayName("Should return not found when trying to retrieve a non-existent favorite product")
    void testGetFavorite_NotFound() throws Exception {

        Optional<ClientEntity> mockClient = Optional.of(ClientEntity.builder()
                .clientId(1L)
                .name("Bruce Wayne")
                .email("batman@morcegao.com")
                .favoriteProducts(
                        Set.of(ProductEntity.builder()
                                .productId(1L)
                                .productIdLuizaLabs(1L)
                                .image("http://minhaimage.png")
                                .title("Produto Oficial")
                                .price(88.55)
                                .reviewScore("4.5")
                                .build()))
                .build());

        String clientId = "1";
        String productIdLuizaLabs = "9";

        when(clientRepository.findById(anyLong())).thenReturn(mockClient);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/client/{clientId}/favorites/product/{productIdLuizaLabs}", clientId, productIdLuizaLabs))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Product not already register: 9"))
                .andExpect(jsonPath("$.path").value("/client/1/favorites/product/9"))
                .andExpect(jsonPath("$.date").exists());
    }

    @Test
    @DisplayName("Should successfully retrieve all favorite products when input data is valid")
    void testGetAllFavorites_Success() throws Exception {
        Optional<ClientEntity> mockClient = Optional.of(ClientEntity.builder()
                .clientId(1L)
                .name("Bruce Wayne")
                .email("batman@morcegao.com")
                .favoriteProducts(
                        Set.of(
                                ProductEntity.builder()
                                        .productId(1L)
                                        .productIdLuizaLabs(1L)
                                        .image("http://minhaimage.png")
                                        .title("Produto Oficial 1")
                                        .price(88.55)
                                        .reviewScore("4.5")
                                        .build(),
                                ProductEntity.builder()
                                        .productId(2L)
                                        .productIdLuizaLabs(2L)
                                        .image("http://minhaimage2.png")
                                        .title("Produto Oficial 2")
                                        .price(99.99)
                                        .reviewScore("4.7")
                                        .build()
                        ))
                .build());

        String clientId = "1";

        when(clientRepository.findById(anyLong())).thenReturn(mockClient);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/client/{clientId}/favorites", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bruce Wayne"))
                .andExpect(jsonPath("$.email").value("batman@morcegao.com"))
                .andExpect(jsonPath("$.favoritesProducts[*].id", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$.favoritesProducts[*].title", containsInAnyOrder("Produto Oficial 1", "Produto Oficial 2")));
    }


}
