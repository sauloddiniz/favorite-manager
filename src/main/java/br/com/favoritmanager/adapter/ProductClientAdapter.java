package br.com.favoritmanager.adapter;

import br.com.favoritmanager.adapter.feign.client.DTO.ProductResponseDTO;
import br.com.favoritmanager.adapter.feign.client.ProductClientApiFeign;
import br.com.favoritmanager.adapter.output.ProductClientPort;
import br.com.favoritmanager.core.model.Product;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProductClientAdapter implements ProductClientPort {

    private final ProductClientApiFeign productClientApiFeign;

    public ProductClientAdapter(ProductClientApiFeign productClientApiFeign) {
        this.productClientApiFeign = productClientApiFeign;
    }

    @Override
    public Product getProductByIdLuizaLabs(Long productIdLuizaLabs) {
        try {
            return ProductResponseDTO.toProduct(Objects.requireNonNull(productClientApiFeign.getProductByIdLuizaLabs(productIdLuizaLabs).getBody()));
        } catch (Exception exception) {
            return null;
        }
    }
}
