package br.com.favoritemanager.application.usecase;

import br.com.favoritemanager.adapter.ProductClientAdapter;
import br.com.favoritemanager.adapter.input.DTO.ClientAndListFavoritesResponseDTO;
import br.com.favoritemanager.adapter.output.ClientPersistencePort;
import br.com.favoritemanager.adapter.output.ProductClientPort;
import br.com.favoritemanager.adapter.output.ProductPersistencePort;
import br.com.favoritemanager.core.exception.FavoriteListEmptyException;
import br.com.favoritemanager.core.exception.ProductNotAlreadyRegister;
import br.com.favoritemanager.core.model.Client;
import br.com.favoritemanager.core.model.Product;
import br.com.favoritemanager.core.exception.ProductAlreadyExistException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

@Service
public class ProductUseCaseImpl implements ProductUseCase {

    private final ClientPersistencePort clientPersistencePort;
    private final ProductPersistencePort productPersistencePort;
    private final ProductClientPort productClientAdapter;

    public ProductUseCaseImpl(ProductPersistencePort productPersistencePort, ClientPersistencePort clientPersistencePort, ProductClientAdapter productClientAdapter) {
        this.productPersistencePort = productPersistencePort;
        this.clientPersistencePort = clientPersistencePort;
        this.productClientAdapter = productClientAdapter;
    }

    @Override
    public void saveProduct(Long clientId, Long productId) {
        Client client = clientPersistencePort.getClientById(clientId);
        Product product = productClientAdapter.getProductByIdLuizaLabs(productId);
        boolean isSuccesses = client.addProductInFavorite(product);
        validateFavoriteAddition(isSuccesses, productId);
        productPersistencePort.saveProduct(product);
    }

    @Override
    public ClientAndListFavoritesResponseDTO getProducts(Long clientId) {
        Client client = clientPersistencePort.getClientById(clientId);
        return ClientAndListFavoritesResponseDTO.toResponse(client);
    }

    @Override
    public void removeFavoriteProduct(Long clientId, Long productId) {
        Client client = clientPersistencePort.getClientById(clientId);
        Product product = findFavoriteProduct(client.getFavoriteProducts(), productId);
        product.setClient(client);
        productPersistencePort.deleteProduct(product);
    }

    @Override
    public ClientAndListFavoritesResponseDTO getFavorite(Long clientId, Long productId) {
        Client client = clientPersistencePort.getClientById(clientId);
        Product product = findFavoriteProduct(client.getFavoriteProducts(), productId);
        client.getOnlyRegister(product);
        return ClientAndListFavoritesResponseDTO.toResponse(client);
    }

    private Product findFavoriteProduct(Set<Product> favoriteProducts, Long productId) {
        return Optional.ofNullable(favoriteProducts)
                .filter(isNotEmpty())
                .orElseThrow(FavoriteListEmptyException::new)
                .stream()
                .filter(product -> product.getProductExternalId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotAlreadyRegister(productId.toString()));
    }

    private static Predicate<Set<Product>> isNotEmpty() {
        return set -> !set.isEmpty();
    }

    private void validateFavoriteAddition(boolean isSuccesses, Long productId) {
        if (Boolean.FALSE.equals(isSuccesses)) {
            throw new ProductAlreadyExistException(productId.toString());
        }
    }
}
