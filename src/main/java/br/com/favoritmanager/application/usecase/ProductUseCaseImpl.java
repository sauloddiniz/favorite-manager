package br.com.favoritmanager.application.usecase;

import br.com.favoritmanager.adapter.ClientPersistenceAdapter;
import br.com.favoritmanager.adapter.ProductClientAdapter;
import br.com.favoritmanager.adapter.output.ClientPersistencePort;
import br.com.favoritmanager.adapter.output.ProductClientPort;
import br.com.favoritmanager.adapter.output.ProductPersistencePort;
import br.com.favoritmanager.core.model.Client;
import br.com.favoritmanager.core.model.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

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
        product.setClient(client);
        productPersistencePort.saveProduct(product);
    }
}
