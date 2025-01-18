package br.com.favoritmanager.adapter.input;

import br.com.favoritmanager.adapter.feign.client.DTO.ProductResponseDTO;
import br.com.favoritmanager.adapter.feign.client.ProductClientApiFeign;
import br.com.favoritmanager.adapter.persistence.entity.ClientEntity;
import br.com.favoritmanager.adapter.persistence.entity.ProductEntity;
import br.com.favoritmanager.adapter.persistence.repository.ClientRepository;
import br.com.favoritmanager.adapter.persistence.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

        when(clientRepository.findById(eq(1L)))
                .thenReturn(mockClient);
        when(productClientApiFeign.getProductByIdLuizaLabs(eq(1L)))
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

        when(clientRepository.findById(eq(1L))).thenReturn(mockClient);
        when(productClientApiFeign.getProductByIdLuizaLabs(eq(1L)))
                .thenReturn(new ResponseEntity<>(mockResponseApi, HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/client/{clientId}/favorites/product/{productIdLuizaLabs}", clientId, productIdLuizaLabs))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Product already exist in favorites: 1"))
                .andExpect(jsonPath("$.path").value("/client/1/favorites/product/1"))
                .andExpect(jsonPath("$.date-hour").exists());

    }

}
