package br.com.favoritemanager.adapter;

import br.com.favoritemanager.adapter.feign.client.DTO.ProductResponseDTO;
import br.com.favoritemanager.adapter.feign.client.ProductClientApiFeign;
import br.com.favoritemanager.adapter.output.ProductClientPort;
import br.com.favoritemanager.core.exception.ProductNotAlreadyRegister;
import br.com.favoritemanager.core.model.Product;
import feign.FeignException.FeignClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class ProductClientAdapter implements ProductClientPort {

    private final ProductClientApiFeign productClientApiFeign;

    public ProductClientAdapter(ProductClientApiFeign productClientApiFeign) {
        this.productClientApiFeign = productClientApiFeign;
    }

    @Override
    public Product getProductByIdLuizaLabs(Long productId) {
        try {
            ResponseEntity<ProductResponseDTO> responseEntity =
                    productClientApiFeign.getProductByIdLuizaLabs(productId);
            return ProductResponseDTO.toProduct(Objects.requireNonNull(responseEntity.getBody()));
        } catch (FeignClientException exception) {
            if (isProductNotFound(exception)) {
                throw new ProductNotAlreadyRegister(productId.toString());
            }
            log.error(exception.getMessage(), exception);
            throw exception;
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    private static boolean isProductNotFound(FeignClientException exception) {
        return exception.status() == 404;
    }

}
