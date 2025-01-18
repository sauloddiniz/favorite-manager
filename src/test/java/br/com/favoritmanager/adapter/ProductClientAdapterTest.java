package br.com.favoritmanager.adapter;

import br.com.favoritmanager.adapter.feign.client.DTO.ProductResponseDTO;
import br.com.favoritmanager.adapter.feign.client.ProductClientApiFeign;
import br.com.favoritmanager.core.exception.ProductNotAlreadyRegister;
import br.com.favoritmanager.core.model.Product;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductClientAdapterTest {

    @Mock
    ProductClientApiFeign productClientApiFeign;

    @InjectMocks
    ProductClientAdapter productClientAdapter;

    @Test
    @DisplayName("Should return product when valid productId is provided")
    void shouldReturnProductWhenValidProductIdIsProvided() {

        Long validProductId = 123L;
        ProductResponseDTO mockResponse = new ProductResponseDTO(
                validProductId,
                "http://example.com/image.jpg",
                99.99,
                "Sample Product",
                "4.5"
        );
        ResponseEntity<ProductResponseDTO> responseEntity =
                new ResponseEntity<>(mockResponse, HttpStatus.OK);

        when(productClientApiFeign.getProductByIdLuizaLabs(anyLong()))
                .thenReturn(responseEntity);

        Product result = productClientAdapter.getProductByIdLuizaLabs(validProductId);

        assertNotNull(result);
        assertEquals(validProductId, result.getProductIdLuizaLabs());
        assertEquals("http://example.com/image.jpg", result.getImage());
        assertEquals(99.99, result.getPrice());
        assertEquals("Sample Product", result.getTitle());
        assertEquals("4.5", result.getReviewScore());

        verify(productClientApiFeign, times(1)).getProductByIdLuizaLabs(validProductId);
    }

    @Test
    @DisplayName("Should throw ProductNotAlreadyRegister when product is not found")
    void shouldThrowProductNotAlreadyRegisterWhenProductIsNotFound() {

        FeignException mockException = mock(FeignException.FeignClientException.class);

        Long invalidProductId = 999L;
        when(mockException.status()).thenReturn(404);
        when(productClientApiFeign.getProductByIdLuizaLabs(anyLong()))
                .thenThrow(mockException);

        ProductNotAlreadyRegister exception = assertThrows(ProductNotAlreadyRegister.class,
                () -> productClientAdapter.getProductByIdLuizaLabs(invalidProductId));

        assertEquals("Product not already register: 999", exception.getMessage());
        verify(productClientApiFeign, times(1)).getProductByIdLuizaLabs(invalidProductId);
    }

    @Test
    @DisplayName("Should throw Exception when an unexpected error occurs")
    void shouldThrowExceptionWhenUnexpectedErrorOccurs() {
        Long productId = 123L;

        when(productClientApiFeign.getProductByIdLuizaLabs(productId))
                .thenThrow(new RuntimeException("Unexpected error"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> productClientAdapter.getProductByIdLuizaLabs(productId));

        assertEquals("Unexpected error", exception.getMessage());
        verify(productClientApiFeign, times(1)).getProductByIdLuizaLabs(productId);
    }
}
