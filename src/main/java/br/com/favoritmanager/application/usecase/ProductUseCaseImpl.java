package br.com.favoritmanager.application.usecase;

import br.com.favoritmanager.adapter.ClientPersistenceAdapter;
import br.com.favoritmanager.adapter.ProductClientAdapter;
import br.com.favoritmanager.adapter.input.DTO.ClientAndListFavoritesResponseDTO;
import br.com.favoritmanager.adapter.output.ClientPersistencePort;
import br.com.favoritmanager.adapter.output.ProductClientPort;
import br.com.favoritmanager.adapter.output.ProductPersistencePort;
import br.com.favoritmanager.core.exception.FavoriteListEmptyException;
import br.com.favoritmanager.core.exception.ProductNotAlreadyRegister;
import br.com.favoritmanager.core.model.Client;
import br.com.favoritmanager.core.model.Product;
import br.com.favoritmanager.core.exception.ProductAlreadyExistException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

@Service
public class ProductUseCaseImpl implements ProductUseCase {

    private final ClientPersistencePort clientPersistencePort;
    private final ProductPersistencePort productPersistencePort;
    private final ProductClientPort productClientAdapter;

    public ProductUseCaseImpl(ProductPersistencePort productPersistencePort, ClientPersistenceAdapter clientPersistenceAdapter, ProductClientPort productClientPort, ClientPersistencePort clientPersistencePort, ProductClientAdapter productClientAdapter) {
        this.productPersistencePort = productPersistencePort;
        this.clientPersistencePort = clientPersistencePort;
        this.productClientAdapter = productClientAdapter;
    }

    @Override
    public void saveProduct(Long clientId, Long productLuizaLabsId) {
        Client client = clientPersistencePort.getClientById(clientId);
        Product product = productClientAdapter.getProductByIdLuizaLabs(productLuizaLabsId);
        boolean isSuccesses = client.addProductInFavorite(product);
        validAddProductInFavorite(isSuccesses, productLuizaLabsId);
        productPersistencePort.saveProduct(product);
    }

    @Override
    public ClientAndListFavoritesResponseDTO getProducts(Long clientId) {
        Client client = clientPersistencePort.getClientById(clientId);
        return ClientAndListFavoritesResponseDTO.toResponse(client);
    }

    @Override
    public void removeProductFavorites(Long clientId, Long productIdLuizaLabs) {
        Client client = clientPersistencePort.getClientById(clientId);
        Product product = findFavoriteProduct(client.getFavoriteProducts(), productIdLuizaLabs);
        product.setClient(client);
        productPersistencePort.deleteProduct(product);
    }

    @Override
    public ClientAndListFavoritesResponseDTO getFavorite(Long clientId, Long productIdLuizaLabs) {
        Client client = clientPersistencePort.getClientById(clientId);
        Product product = findFavoriteProduct(client.getFavoriteProducts(), productIdLuizaLabs);
        client.onlyRegister(product);
        return ClientAndListFavoritesResponseDTO.toResponse(client);
    }

    private Product findFavoriteProduct(Set<Product> favoriteProducts, Long productIdLuizaLabs) {
        return Optional.ofNullable(favoriteProducts)
                .filter(isNotEmpty())
                .orElseThrow(FavoriteListEmptyException::new)
                .stream()
                .filter(product -> product.getProductIdLuizaLabs().equals(productIdLuizaLabs))
                .findFirst()
                .orElseThrow(() -> new ProductNotAlreadyRegister(productIdLuizaLabs.toString()));
    }

    private static Predicate<Set<Product>> isNotEmpty() {
        return set -> !set.isEmpty();
    }

    private void validAddProductInFavorite(boolean isSuccesses, Long productLuizaLabsId) {
        if (Boolean.FALSE.equals(isSuccesses)) {
            throw new ProductAlreadyExistException(productLuizaLabsId.toString());
        }
    }
}
