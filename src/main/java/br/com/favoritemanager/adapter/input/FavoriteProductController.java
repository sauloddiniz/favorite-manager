package br.com.favoritemanager.adapter.input;

import br.com.favoritemanager.adapter.input.DTO.ClientAndListFavoritesResponseDTO;
import br.com.favoritemanager.application.usecase.ProductUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequestMapping("/client/{clientId}/favorites")
@RestController
public class FavoriteProductController {

    private final ProductUseCase productUseCase;

    public FavoriteProductController(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<Void> saveFavoriteProduct(@PathVariable Long clientId, @PathVariable Long productId) {
        productUseCase.saveProduct(clientId, productId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/client/{clientId}/favorites/product/{productId}")
                .buildAndExpand(clientId.toString(), productId.toString())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> removeFavoriteProduct(@PathVariable Long clientId, @PathVariable Long productId) {
        productUseCase.removeFavoriteProduct(clientId, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ClientAndListFavoritesResponseDTO> getFavorite(@PathVariable Long clientId, @PathVariable Long productId) {
        ClientAndListFavoritesResponseDTO response = productUseCase.getFavorite(clientId, productId);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<ClientAndListFavoritesResponseDTO> getAllFavorites(@PathVariable Long clientId) {
        ClientAndListFavoritesResponseDTO response = productUseCase.getProducts(clientId);
        return ResponseEntity.ok(response);
    }
}
